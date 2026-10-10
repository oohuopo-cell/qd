package com.wkvip.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class WkVipLicense {
    private static final String B32 = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final long DAY_MS = 86400000;
    private static final String K_CARD = "card";
    private static final String K_EXPIRE = "expire_at";
    private static final String K_FP = "fp";
    private static final String ORIG_CERT_HEX = "3082035b30820243a00302010202045ff9ba5b300d06092a864886f70d01010b0500305d310c300a06035504061303303836310e300c060355040813054368696e61310e300c060355040713054368696e61310e300c060355040a13054368696e61310e300c060355040b13054368696e61310d300b06035504031304476f4b753020170d3139303831383137313530345a180f32313139303732353137313530345a305d310c300a06035504061303303836310e300c060355040813054368696e61310e300c060355040713054368696e61310e300c060355040a13054368696e61310e300c060355040b13054368696e61310d300b06035504031304476f4b7530820122300d06092a864886f70d01010105000382010f003082010a02820101009d5fbccc3f84e1ef19d31480a48e28af65553e2b780201065d18b3d9a88033f33c0d7923e4f7243193279f09825a59b17561a5b6ee4612c14af8211f504e098c1d124440c0e76054b13edd3d0f9cdc5a31ef8ccaaf7e1ab04483587aa2e355c2aa236f0acf43d8c4d03b319a5cb827415aa8accf88287bcab10bf0c1bef652dfa050c6353f16729a0b288438e069b8d9d2c8c9658c101b51c0d150edd6d62d83637fdc8f8f5bb4200be6110ba1bfd9fe3988f85f559c23144626914a44b1ca1cd4f1abcad80d7fff6b27f8f3e2677c5fb5d224a9334e7700e4304ee95488cfcca5242e616d7a81334b9220d7e5a8fdc9519ef550b11b2ccbfc3ed759c98b94290203010001a321301f301d0603551d0e04160414a6284692d771fc80da1930c0520b4c7403710f55300d06092a864886f70d01010b0500038201010095d80c78acdacd6abfb4cbc974e25ef0481211e2132a2e1e9bbb6d2a95f1e679c7e65799bf6ace9802e7cbaf841bf74d292451653baf9f2b4f6f1af6f36815dedb123db0cd6f2352030834029099cbea60f4824cb9477a190f41b9dc16e70c61b194168563f92b22042713ec6e4aece8e8e8078aeabfe59cde5e8c53e03fbc8d0798d929c5d1de4a67b86bac179ecba54e7b3850e320cb8b78a2638af612f79ee8ed6dce31cef04d81ba447ab1b47c50e6bb5d808d0aee68899649592302fd88e009d78846801288b3a53c11ab9a5900be0f3ce143c46f2fbae103e27209b1b3661508b846da0bec2946c58b7941aad759ffe575dd018deeddb6aa9b85a80334";
    private static final String PREFS = "wklic_store";
    private static final String SECRET = "wkvip-2024-secret-key-v1";
    private static final String TAG = "[WKVIP-LIC] ";
    private static volatile byte[] sOrigCertBytes = null;

    private WkVipLicense() {
    }

    public static String activate(Context context, String str) {
        if (str == null) {
            return "请输入卡密";
        }
        String trim = str.trim();
        if (trim.isEmpty()) {
            return "请输入卡密";
        }
        Map<String, String> parseCard = parseCard(trim);
        if (parseCard == null) {
            return "卡密格式错误或签名无效";
        }
        int parseInt = Integer.parseInt(parseCard.get("year"));
        int parseInt2 = Integer.parseInt(parseCard.get("month"));
        Calendar calendar = Calendar.getInstance();
        if (parseInt != calendar.get(1) || parseInt2 != calendar.get(2) + 1) {
            return "卡密已过激活期限（仅限生成当月激活）";
        }
        save(context, trim, System.currentTimeMillis() + Long.parseLong(parseCard.get("validity")));
        return null;
    }

    private static String b32encode(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        for (byte b : bArr) {
            i = (i << 8) | (b & 255);
            i2 += 8;
            while (i2 >= 5) {
                i2 -= 5;
                sb.append(B32.charAt((i >> i2) & 31));
            }
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    public static void clear(Context context) {
        prefs(context).edit().clear().apply();
    }

    private static long decodeBase32Long(String str) {
        long j = 0;
        for (char c : str.toCharArray()) {
            int indexOf = B32.indexOf(c);
            if (indexOf < 0) {
                return 0L;
            }
            j = (j * 32) + indexOf;
        }
        return j;
    }

    public static String getFingerprint(Context context) {
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            StringBuilder sb = new StringBuilder();
            if (string == null) {
                string = "";
            }
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(sb.append(string).append("|").append(Build.BRAND).append("|").append(Build.MODEL).append("|").append(Build.VERSION.RELEASE).toString().getBytes("UTF-8"));
            StringBuilder sb2 = new StringBuilder();
            for (byte b : digest) {
                sb2.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb2.toString().substring(0, 16);
        } catch (Throwable th) {
            return "unknown";
        }
    }

    public static byte[] getOriginalCert() {
        if (sOrigCertBytes != null) {
            return sOrigCertBytes;
        }
        try {
            int length = ORIG_CERT_HEX.length();
            byte[] bArr = new byte[length / 2];
            for (int i = 0; i < length; i += 2) {
                bArr[i / 2] = (byte) ((Character.digit(ORIG_CERT_HEX.charAt(i), 16) << 4) + Character.digit(ORIG_CERT_HEX.charAt(i + 1), 16));
            }
            sOrigCertBytes = bArr;
            return bArr;
        } catch (Throwable th) {
            return null;
        }
    }

    private static String hmacSha256(String str) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes("UTF-8"), "HmacSHA256"));
            return b32encode(mac.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isValid(Context context) {
        try {
            SharedPreferences prefs = prefs(context);
            if (getFingerprint(context).equals(prefs.getString(K_FP, ""))) {
                return prefs.getLong(K_EXPIRE, 0L) > System.currentTimeMillis();
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009e, code lost:
    
        if (r4.equals("T") != false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.util.Map<java.lang.String, java.lang.String> parseCard(java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wkvip.module.WkVipLicense.parseCard(java.lang.String):java.util.Map");
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, 0);
    }

    private static void save(Context context, String str, long j) {
        prefs(context).edit().putString(K_CARD, str).putLong(K_EXPIRE, j).putString(K_FP, getFingerprint(context)).apply();
    }

    public static String storedCard(Context context) {
        return prefs(context).getString(K_CARD, "");
    }
}
