// =====================================================================
//  WkVipLicense —— 卡密授权核心（与 cardgen.py / WkVipBridge.java 算法完全一致）
//
//  卡密格式: wk-XXXXX-XXXXX-XXXXX-XXXXX  (20 个 base32 字符)
//  布局: type(1) year(4) month(2) validity(6) random(3) sig(4)
//    type: M=月卡 Y=年卡 F=永久卡 T=测试卡
//    year: 公历年 (base32, 4位)
//    month: 月份 1-12 (base32, 2位)
//    validity: 有效期天数 (base32, 6位)
//    random: 随机盐 (base32, 3位)
//    sig: HMAC-SHA256(payload) 前 4 位 base32
//
//  激活规则: 所有卡密必须在「生成当年当月」激活，跨月作废；
//            激活后按卡时长有效期计算，跨月可正常使用。
// =====================================================================

package com.wkvip.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WkVipLicense {

    private static final String TAG = "[WKVIP-LIC] ";

    // ============ 必须和 cardgen.py 一致 ============
    private static final String SECRET = "wkvip-2024-secret-key-v1";
    private static final String B32 = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    // ============================================

    private static final long DAY_MS = 24L * 60 * 60 * 1000;

    private static final String PREFS = "wklic_store";
    private static final String K_CARD = "card";
    private static final String K_EXPIRE = "expire_at";
    private static final String K_FP = "fp";

    // 原始签名证书 DER (hex)，从原版 wk.apk 的 META-INF/WUKONG.RSA 提取
    // 用于 LSPatch 重签名后欺骗 360 加固的签名校验
    private static final String ORIG_CERT_HEX =
            "3082035b30820243a00302010202045ff9ba5b300d06092a864886f70d01010b0500305d310c300a06035504061303303836310e300c060355040813054368696e61310e300c060355040713054368696e61310e300c060355040a13054368696e61310e300c060355040b13054368696e61310d300b06035504031304476f4b75"
            + "3020170d3139303831383137313530345a180f32313139303732353137313530345a305d310c300a06035504061303303836310e300c060355040813054368696e61310e300c060355040713054368696e61310e300c060355040a13054368696e61310e300c060355040b13054368696e61310d300b06035504031304476f4b75"
            + "30820122300d06092a864886f70d01010105000382010f003082010a02820101009d5fbccc3f84e1ef19d31480a48e28af65553e2b780201065d18b3d9a88033f33c0d7923e4f7243193279f09825a59b17561a5b6ee4612c14af8211f504e098c1d124440c0e76054b13edd3d0f9cdc5a31ef8ccaaf7e1ab04483587aa2e355c2aa236f0acf43d8c4d03b319a5cb827415aa8accf88287bcab10bf0c1bef652dfa050c6353f16729a0b288438e069b8d9d2c8c9658c101b51c0d150edd6d62d83637fdc8f8f5bb4200be6110ba1bfd9fe3988f85f559c23144626914a44b1ca1cd4f1abcad80d7fff6b27f8f3e2677c5fb5d224a9334e7700e4304ee95488cfcca5242e616d7a81334b9220d7e5a8fdc9519ef550b11b2ccbfc3ed759c98b9429"
            + "0203010001a321301f301d0603551d0e04160414a6284692d771fc80da1930c0520b4c7403710f55300d06092a864886f70d01010b0500038201010095d80c78acdacd6abfb4cbc974e25ef0481211e2132a2e1e9bbb6d2a95f1e679c7e65799bf6ace9802e7cbaf841bf74d292451653baf9f2b4f6f1af6f36815dedb123db0cd6f2352030834029099cbea60f4824cb9477a190f41b9dc16e70c61b194168563f92b22042713ec6e4aece8e8e8078aeabfe59cde5e8c53e03fbc8d0798d929c5d1de4a67b86bac179ecba54e7b3850e320cb8b78a2638af612f79ee8ed6dce31cef04d81ba447ab1b47c50e6bb5d808d0aee68899649592302fd88e009d78846801288b3a53c11ab9a5900be0f3ce143c46f2fbae103e27209b1b3661508b846da0bec2946c58b7941aad759ffe575dd018deeddb6aa9b85a80334";

    private static volatile byte[] sOrigCertBytes = null;

    public static byte[] getOriginalCert() {
        if (sOrigCertBytes != null) return sOrigCertBytes;
        try {
            String hex = ORIG_CERT_HEX;
            int len = hex.length();
            byte[] out = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                out[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                        + Character.digit(hex.charAt(i + 1), 16));
            }
            sOrigCertBytes = out;
            return out;
        } catch (Throwable e) {
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private WkVipLicense() { }

    // ---- 设备指纹 ----
    public static String getFingerprint(Context ctx) {
        try {
            String aid = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
            String raw = (aid == null ? "" : aid) + "|" + Build.BRAND + "|" + Build.MODEL
                    + "|" + Build.VERSION.RELEASE;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(raw.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString().substring(0, 16);
        } catch (Throwable e) {
            return "unknown";
        }
    }

    private static SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static boolean isValid(Context ctx) {
        try {
            SharedPreferences p = prefs(ctx);
            String fp = p.getString(K_FP, "");
            if (!getFingerprint(ctx).equals(fp)) return false;
            long expire = p.getLong(K_EXPIRE, 0);
            return expire > System.currentTimeMillis();
        } catch (Throwable e) {
            return false;
        }
    }

    public static String storedCard(Context ctx) {
        return prefs(ctx).getString(K_CARD, "");
    }

    // ---- base32 工具 ----
    private static long decodeBase32Long(String s) {
        long val = 0;
        for (char c : s.toCharArray()) {
            int idx = B32.indexOf(c);
            if (idx < 0) return 0;
            val = val * 32 + idx;
        }
        return val;
    }

    private static String b32encode(byte[] data) {
        StringBuilder sb = new StringBuilder();
        int buffer = 0, bits = 0;
        for (byte b : data) {
            buffer = (buffer << 8) | (b & 0xff);
            bits += 8;
            while (bits >= 5) {
                bits -= 5;
                sb.append(B32.charAt((buffer >> bits) & 31));
            }
        }
        return sb.toString();
    }

    private static String hmacSha256(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes("UTF-8"), "HmacSHA256"));
            byte[] hash = mac.doFinal(data.getBytes("UTF-8"));
            return b32encode(hash);
        } catch (Exception e) {
            return "";
        }
    }

    // ---- 解析卡密，返回 null 表示无效 ----
    private static Map<String, String> parseCard(String card) {
        if (card == null) return null;
        card = card.trim().toUpperCase();
        if (!card.startsWith("WK-")) return null;
        String body = card.substring(3).replace("-", "");
        if (body.length() != 20) return null;
        for (char c : body.toCharArray()) {
            if (B32.indexOf(c) < 0) return null;
        }
        String type = body.substring(0, 1);
        int year = (int) decodeBase32Long(body.substring(1, 5));
        int month = (int) decodeBase32Long(body.substring(5, 7));
        String sig = body.substring(16, 20);

        String payload = type + body.substring(1, 16);
        String expectedSig = hmacSha256(payload).substring(0, 4);
        if (!expectedSig.equals(sig)) return null;

        long days;
        switch (type) {
            case "M": days = 30; break;
            case "Y": days = 365; break;
            case "F": days = 36500; break;
            case "T": days = 1; break;
            default: return null;
        }
        if (month < 1 || month > 12) return null;
        if (year < 2020 || year > 2100) return null;

        Map<String, String> m = new HashMap<>();
        m.put("type", type);
        m.put("year", String.valueOf(year));
        m.put("month", String.valueOf(month));
        m.put("validity", String.valueOf(days * DAY_MS));
        return m;
    }

    // ---- 激活卡密，返回 null 表示成功，否则返回错误信息 ----
    public static String activate(Context ctx, String card) {
        if (card == null) return "请输入卡密";
        card = card.trim();
        if (card.isEmpty()) return "请输入卡密";

        Map<String, String> info = parseCard(card);
        if (info == null) return "卡密格式错误或签名无效";

        int genYear = Integer.parseInt(info.get("year"));
        int genMonth = Integer.parseInt(info.get("month"));
        Calendar cal = Calendar.getInstance();
        if (genYear != cal.get(Calendar.YEAR) || genMonth != (cal.get(Calendar.MONTH) + 1)) {
            return "卡密已过激活期限（仅限生成当月激活）";
        }

        long validityMs = Long.parseLong(info.get("validity"));
        long expire = System.currentTimeMillis() + validityMs;
        save(ctx, card, expire);
        return null;
    }

    private static void save(Context ctx, String card, long expireAt) {
        prefs(ctx).edit()
                .putString(K_CARD, card)
                .putLong(K_EXPIRE, expireAt)
                .putString(K_FP, getFingerprint(ctx))
                .apply();
    }

    public static void clear(Context ctx) {
        prefs(ctx).edit().clear().apply();
    }
}
