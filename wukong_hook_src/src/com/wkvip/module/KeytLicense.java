// =====================================================================
//  KeytLicense —— 卡密通 (keyt.cn) 联网验证
//
//  存储方案：system property (persist.*) + 文件双写
//  分隔符用 :: 避免 shell 管道符问题
//  清数据、卸载、重启都不影响
// =====================================================================

package com.wkvip.module;

import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class KeytLicense {

    private static final String TAG = "[KeytLicense]";

    // ============ 卡密通配置 ============
    private static final String USER_NAME = "a1165707085";
    private static final String APP_NAME = "wk";
    private static final String SIGN_KEY = "9107e61c0f4ae987218827ef8c78cbeb";
    private static final String BASE_URL = "https://www.keyt.cn/kami/" + USER_NAME + "/check.php";
    private static final int TIMEOUT_MS = 15000;
    private static final int TIMESTAMP_MAX_DIFF = 120;
    // ==================================

    // 存储方案
    private static final String PROP_KEY = "persist.wk.lic";
    private static final String[] LIC_PATHS = {
        "/data/local/tmp/wklic.dat",
        "/sdcard/wklic.dat"
    };
    // 分隔符用 :: 而不是 |，避免 shell 管道符问题
    private static final String SEP = "::";

    // ============ 模块进程内存储(SharedPreferences) ============
    // 旧版只写 /data/local/tmp + /sdcard 文件,模块进程(=目标 App 进程)在无 root
    // 时读不到 /data/local/tmp,/sdcard 又可能缺存储权限 -> 卡密验证功能形同虚设。
    // v15 新增 SharedPreferences 写入:模块进程内 getSharedPreferences 一定可读可写,
    // 闸门 UI 在目标 App 进程内激活时直接落库,下次 onResume 立刻认。
    // 键沿用旧 WkVipLicense 风格:card / expire_at / fp,防与文件格式混淆。
    private static final String PREFS_NAME = "wklic_store";
    private static final String K_CARD  = "card";
    private static final String K_EXPIRE = "expire_at";
    private static final String K_FP    = "fp";
    // =============================================================

    private KeytLicense() {
    }

    // ---- 设备机器码 (mac) ----
    public static String getMachineCode(Context ctx) {
        try {
            String raw = Build.BRAND + Build.MODEL + Build.DEVICE
                    + Build.PRODUCT + Build.MANUFACTURER
                    + Build.FINGERPRINT + Build.VERSION.RELEASE;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest(raw.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) {
                sb.append(String.format("%02X", b & 0xff));
            }
            return sb.toString().substring(0, 16);
        } catch (Throwable e) {
            return "UNKNOWN0000000000";
        }
    }

    // ---- MD5 ----
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Throwable e) {
            return "";
        }
    }

    // ---- HTTP GET ----
    private static String httpGet(String urlString) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Linux; Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + ")");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Cache-Control", "no-cache");

            int code = conn.getResponseCode();
            log("HTTP 响应码: " + code);
            if (code == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
        } catch (Throwable e) {
            log("httpGet 异常: " + e);
        } finally {
            if (conn != null) {
                try { conn.disconnect(); } catch (Throwable ignore) {}
            }
        }
        return "";
    }

    // ---- 验证服务器返回 ----
    private static String verifyResponse(String raw) {
        if (raw == null || raw.isEmpty()) return null;
        raw = raw.trim();
        log("verifyResponse 原始: " + raw);

        int signIndex = raw.indexOf("|sign=");
        if (signIndex == -1) {
            log("未找到 |sign= 标记");
            return null;
        }

        String body = raw.substring(0, signIndex);
        String sign = raw.substring(signIndex + 6);

        String localSign = md5(body + SIGN_KEY);
        log("本地签名: " + localSign);
        log("服务器签名: " + sign);

        if (!localSign.equals(sign)) {
            log("签名不匹配");
            return null;
        }
        log("签名校验通过");

        int lastPipe = body.lastIndexOf('|');
        if (lastPipe == -1) {
            log("未找到时间戳分隔符");
            return null;
        }

        String tsStr = body.substring(lastPipe + 1);
        try {
            long serverTs = Long.parseLong(tsStr.trim());
            long localTs = System.currentTimeMillis() / 1000;
            long diff = Math.abs(localTs - serverTs);
            log("服务器时间戳: " + serverTs + " 本地: " + localTs + " 差: " + diff + "秒");

            if (diff > TIMESTAMP_MAX_DIFF) {
                log("时间戳偏差过大");
                return null;
            }
            log("时间戳校验通过");
        } catch (NumberFormatException e) {
            log("时间戳解析失败: " + tsStr);
            return null;
        }

        String biz = body.substring(0, lastPipe);
        log("业务内容: " + biz);
        return biz;
    }

    // ---- 读取 system property（仅反射，不 exec 命令，不阻塞） ----
    private static String getSystemProperty(String key) {
        try {
            Class<?> sp = Class.forName("android.os.SystemProperties");
            Method get = sp.getMethod("get", String.class);
            String val = (String) get.invoke(null, key);
            if (val != null && !val.isEmpty()) {
                log("getprop " + key + " = " + val);
                return val;
            }
        } catch (Throwable e) {
            log("SystemProperties 反射失败: " + e);
        }
        return "";
    }

    // ---- 执行 su 命令（通过 stdin 写入，避免 -c 转义问题） ----
    private static String execSu(String... commands) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String cmd : commands) {
                os.writeBytes(cmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder errSb = new StringBuilder();
            while ((line = errReader.readLine()) != null) {
                errSb.append(line);
            }
            errReader.close();

            p.waitFor();
            if (errSb.length() > 0) {
                log("su stderr: " + errSb);
            }
            return sb.toString();
        } catch (Throwable e) {
            log("execSu 异常: " + e);
            return "";
        }
    }

    // ---- 读取授权 ----
    // quickOnly=true: 只读 system property + 直接读文件（模块用，不调用 su，不阻塞）
    // quickOnly=false: 加上 su 兜底（AuthActivity 用）
    private static String readLicense() {
        return readLicense(false);
    }

    private static String readLicense(boolean quickOnly) {
        return readLicense(quickOnly, null);
    }

    private static String readLicense(boolean quickOnly, Context ctx) {
        // 1. 共享路径
        for (String path : LIC_PATHS) {
            try {
                File f = new File(path);
                if (!f.exists()) continue;
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line = reader.readLine();
                reader.close();
                if (line != null && !line.isEmpty()) {
                    log("从文件读到 " + path + ": " + line);
                    return line;
                }
            } catch (Throwable e) {
                log("读文件失败 " + path + ": " + e.getMessage());
            }
        }

        // 2. app 内部存储（仅 AuthActivity 可读，模块读不到）
        if (ctx != null) {
            try {
                File f = new File(ctx.getFilesDir(), "wklic.dat");
                if (f.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    String line = reader.readLine();
                    reader.close();
                    if (line != null && !line.isEmpty()) {
                        log("从内部存储读到: " + line);
                        return line;
                    }
                }
            } catch (Throwable e) {
                log("读内部存储失败: " + e.getMessage());
            }
        }

        // 3. su 兜底（仅 AuthActivity 用）
        if (quickOnly) return null;
        for (String path : LIC_PATHS) {
            String result = execSu("cat " + path);
            if (result != null && !result.isEmpty()) {
                log("从 su 读到 " + path + ": " + result);
                return result;
            }
        }

        return null;
    }

    // 模块用：快速检查（不调用 su，不阻塞目标进程）
    public static boolean isValidQuick() {
        try {
            String line = readLicense(true);
            if (line == null || line.isEmpty()) return false;

            String[] parts = line.split(SEP);
            if (parts.length < 2) parts = line.split("\\|");
            if (parts.length < 2) return false;

            String card = parts[0].trim();
            long expire = 0;
            try { expire = Long.parseLong(parts[1].trim()); } catch (Throwable ignore) {}

            // 签名校验（防篡改：格式 card::expire::sign）
            if (parts.length >= 3) {
                String sign = parts[2].trim();
                String localSign = md5(card + expire + SIGN_KEY);
                if (!localSign.equalsIgnoreCase(sign)) {
                    log("签名不匹配，授权文件可能被篡改");
                    return false;
                }
            } else {
                // 旧格式无签名，不认可
                return false;
            }

            if (expire > 0) {
                long now = System.currentTimeMillis() / 1000;
                if (now > expire) return false;
            }
            return card != null && !card.isEmpty();
        } catch (Throwable e) {
            return false;
        }
    }

    // ---- 本地缓存检查 ----
    public static boolean isValid(Context ctx) {
        try {
            String line = readLicense(false, ctx);
            if (line == null || line.isEmpty()) {
                log("未找到有效授权");
                return false;
            }

            String[] parts = line.split(SEP);
            if (parts.length < 2) return false;

            String card = parts[0].trim();
            if (card == null || card.isEmpty()) return false;

            long expire = 0;
            try { expire = Long.parseLong(parts[1].trim()); } catch (Throwable ignore) {}

            // 签名校验（防篡改）
            if (parts.length >= 3) {
                String sign = parts[2].trim();
                String localSign = md5(card + expire + SIGN_KEY);
                if (!localSign.equalsIgnoreCase(sign)) {
                    log("签名不匹配，授权文件可能被篡改");
                    return false;
                }
            } else {
                return false;
            }

            if (expire > 0) {
                long nowSec = System.currentTimeMillis() / 1000;
                if (nowSec > expire) {
                    log("卡密已过期: expire=" + expire + " now=" + nowSec);
                    return false;
                }
            }
            log("授权有效: card=" + card);
            return true;
        } catch (Throwable e) {
            log("isValid 异常: " + e);
            return false;
        }
    }

    public static String storedCard(Context ctx) {
        String line = readLicense(false, ctx);
        if (line == null || line.isEmpty()) return "";
        String[] parts = line.split(SEP);
        if (parts.length < 1) parts = line.split("\\|");
        return (parts.length >= 1) ? parts[0] : "";
    }

    public static long expireAt(Context ctx) {
        String line = readLicense(false, ctx);
        if (line == null || line.isEmpty()) return 0L;
        String[] parts = line.split(SEP);
        if (parts.length < 2) parts = line.split("\\|");
        if (parts.length >= 2) {
            try { return Long.parseLong(parts[1].trim()); } catch (Throwable ignore) {}
        }
        return 0L;
    }

    // ---- 联网激活卡密 ----
    public static String activate(Context ctx, String card) {
        if (card == null || card.trim().isEmpty()) {
            return "请输入卡密";
        }
        card = card.trim().toUpperCase();

        log("======= 开始联网验证 =======");
        log("卡密: " + card);

        String mac = getMachineCode(ctx);
        // 官方要求 t 参数格式为 YYYYMMDDHHMMSS（北京时间）
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        String tStr = sdf.format(new java.util.Date());
        log("机器码: " + mac);
        log("时间戳(t): " + tStr);

        String url = BASE_URL
                + "?card=" + card
                + "&mac=" + mac
                + "&app=" + APP_NAME
                + "&heart=1"
                + "&t=" + tStr;

        log("请求URL: " + url);

        String rawResp = httpGet(url);
        if (rawResp == null || rawResp.isEmpty()) {
            return "服务器无响应，请检查网络";
        }

        log("服务器原始返回: [" + rawResp + "]");

        String biz = verifyResponse(rawResp);

        if (biz == null) {
            String lower = rawResp.toLowerCase().trim();
            log("验签失败，检查纯文本响应: " + lower);

            if (lower.contains("too_frequent") || lower.contains("frequent")) {
                return "请求过于频繁，请稍等1分钟再试";
            }

            if (lower.equals("activate") || lower.equals("valide") || lower.equals("valid")
                    || lower.equals("success") || lower.equals("ok") || lower.equals("1")
                    || lower.equals("true")) {
                log("纯文本成功响应: " + rawResp);
                long expire = System.currentTimeMillis() / 1000 + 365 * 24 * 60 * 60;
                saveLicense(ctx, card, expire);
                return null;
            }

            return "验证失败: " + rawResp;
        }

        log("解析业务数据: " + biz);

        if (biz.startsWith("ok|")) {
            log("✅ 验证成功!");
            String[] parts = biz.split("\\|");
            log("biz 分段数: " + parts.length);

            long now = System.currentTimeMillis() / 1000;
            long expireSec = 0;

            if (parts.length >= 3) {
                // ok|xxx|days  或  ok|xxx|days|minutes
                // 注意：minutes 可能是总分钟数（= days*1440），不能和 days 同时加，否则翻倍
                // 策略：优先用 minutes（如果 >= 1440 说明是总分钟数），否则用 days + minutes
                try {
                    int days = Integer.parseInt(parts[2].trim());
                    int minutes = parts.length >= 4 ? Integer.parseInt(parts[3].trim()) : 0;
                    if (minutes >= 1440) {
                        // minutes 是总分钟数，直接用
                        log("剩余: " + minutes + " 分钟 (总分钟数)");
                        expireSec = now + (long) minutes * 60;
                    } else {
                        // minutes 是当天剩余分钟
                        log("剩余: " + days + "天 " + minutes + "分钟");
                        expireSec = now + (long) days * 86400 + (long) minutes * 60;
                    }
                } catch (NumberFormatException e) {
                    log("解析天数失败，parts[2]=" + parts[2]);
                    expireSec = now + 365 * 86400;
                }
            } else if (parts.length >= 2) {
                // ok|permanent
                if ("permanent".equalsIgnoreCase(parts[1].trim())
                        || "forever".equalsIgnoreCase(parts[1].trim())) {
                    log("永久卡密");
                    expireSec = now + 36500L * 86400;
                } else {
                    log("无天数信息，parts[1]=" + parts[1] + "，默认365天");
                    expireSec = now + 365 * 86400;
                }
            } else {
                log("biz 格式异常，默认365天");
                expireSec = now + 365 * 86400;
            }

            saveLicense(ctx, card, expireSec);
            log("授权已保存, card=" + card + " expire=" + expireSec
                    + " (距今 " + (expireSec - now) / 86400 + " 天)");
            return null;

        } else if (biz.startsWith("error|")) {
            String[] parts = biz.split("\\|");
            String errCode = parts.length >= 2 ? parts[1].trim() : "unknown";
            String msg = transMsg(errCode);
            log("❌ 验证失败: " + errCode + " → " + msg);
            return msg;
        }

        log("未知业务数据格式: " + biz);
        return "验证失败: " + biz;
    }

    // ---- 错误码转中文 ----
    private static String transMsg(String code) {
        if (code == null) return "未知错误";
        switch (code.toLowerCase()) {
            case "activate":              return "激活成功";
            case "valid":
            case "valide":                return "验证通过";
            case "permanent":             return "终身有效";
            case "expired":               return "卡密已过期";
            case "banned":                return "卡密已被禁用";
            case "device_mismatch":      return "设备不匹配（此卡密已绑定其他设备）";
            case "online_limit_reached": return "在线设备数已满";
            case "invalid_card":          return "卡密无效";
            case "not_found":             return "卡密不存在";
            case "not_found_card":        return "卡密不存在";
            case "card_error":            return "卡密错误";
            case "mac_error":             return "机器码不匹配";
            case "used":                  return "卡密已被使用";
            case "too_frequent":          return "请求过于频繁，请稍等1分钟再试";
            case "frequent":              return "请求过于频繁，请稍等1分钟再试";
            default:                      return code;
        }
    }

    // ============ 模块进程内 SharedPreferences 读写(无 root 也能用) ============
    // 闸门 UI 在目标 App 进程内激活后,把 card/expire/指纹 落到本进程 SharedPreferences,
    // 模块下次 onResume 直接读 prefs 认授权,不再依赖 /data/local/tmp 文件。
    private static void saveToPrefs(Context ctx, String card, long expireAt) {
        if (ctx == null) return;
        try {
            String fp = getMachineCode(ctx); // 用机器码做绑定,换设备作废
            ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putString(K_CARD, card)
                    .putLong(K_EXPIRE, expireAt)
                    .putString(K_FP, fp)
                    .apply();
            log("SharedPreferences 写入成功: card=" + card + " expire=" + expireAt);
        } catch (Throwable e) {
            log("SharedPreferences 写入失败: " + e.getMessage());
        }
    }

    // 模块进程用:只读本进程 SharedPreferences,不调 su 不阻塞,验指纹
    public static boolean isValidFromPrefs(Context ctx) {
        if (ctx == null) return false;
        try {
            android.content.SharedPreferences p =
                    ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String card = p.getString(K_CARD, "");
            if (card == null || card.isEmpty()) return false;
            long expire = p.getLong(K_EXPIRE, 0L);
            if (expire <= 0) return false;
            long now = System.currentTimeMillis() / 1000;
            if (now > expire) {            // 卡密过期
                log("prefs 卡密已过期: expire=" + expire + " now=" + now);
                return false;
            }
            // 指纹校验:换设备/重装系统后作废
            String fp = p.getString(K_FP, "");
            String expect = getMachineCode(ctx);
            if (!expect.equals(fp)) {
                log("prefs 指纹不匹配,授权作废");
                return false;
            }
            return true;
        } catch (Throwable e) {
            log("isValidFromPrefs 异常: " + e);
            return false;
        }
    }

    public static String cardFromPrefs(Context ctx) {
        if (ctx == null) return "";
        try {
            return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .getString(K_CARD, "");
        } catch (Throwable e) { return ""; }
    }

    public static long expireFromPrefs(Context ctx) {
        if (ctx == null) return 0L;
        try {
            return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .getLong(K_EXPIRE, 0L);
        } catch (Throwable e) { return 0L; }
    }

    // 闸门用：把 provider 信任的卡密+过期镜像到本进程(悟空分身)SharedPreferences,
    // 下次 onResume 走 isValidFromPrefs 快速放行(离线,不联网)
    public static void mirror(Context ctx, String card, long expireAt) {
        saveToPrefs(ctx, card, expireAt);
    }
    // ===================================================================

    // ---- 保存授权 ----
    // 策略：先写 app 内部存储（100% 成功），再用 su 复制到共享路径
    private static void saveLicense(Context ctx, String card, long expireAt) {
        // 格式: card::expire::sign  其中 sign = MD5(card+expire+SIGN_KEY) 防篡改
        String sign = md5(card + expireAt + SIGN_KEY);
        String data = card + SEP + expireAt + SEP + sign;
        log("保存授权数据: " + data);

        // 0. 先写 SharedPreferences(模块进程内可读,无 root 也能用) — v15 新增
        saveToPrefs(ctx, card, expireAt);

        // 1. 先写 app 内部存储（一定成功）
        String internalPath = null;
        try {
            if (ctx != null) {
                File dir = ctx.getFilesDir();
                File f = new File(dir, "wklic.dat");
                FileWriter w = new FileWriter(f);
                w.write(data);
                w.close();
                internalPath = f.getAbsolutePath();
                log("内部存储写入成功: " + internalPath);
            }
        } catch (Throwable e) {
            log("内部存储写入失败: " + e.getMessage());
        }

        // 2. 写到共享路径
        for (String path : LIC_PATHS) {
            // 2a. 直接写
            try {
                File f = new File(path);
                File parent = f.getParentFile();
                if (parent != null) parent.mkdirs();
                FileWriter writer = new FileWriter(f);
                writer.write(data);
                writer.close();
                try { Runtime.getRuntime().exec(new String[]{"chmod", "666", path}).waitFor(); } catch (Throwable ignore) {}
                log("直接写入成功: " + path);
                continue;
            } catch (Throwable e) {
                log("直接写入失败 " + path + ": " + e.getMessage());
            }

            // 2b. 用 su 写（从内部存储复制，避免 shell 转义问题）
            try {
                if (internalPath != null) {
                    execSu("cp " + internalPath + " " + path, "chmod 666 " + path);
                } else {
                    // 用 printf 避免 echo 的转义问题
                    execSu("printf '%s' '" + data + "' > " + path, "chmod 666 " + path);
                }
                log("su 写入完成: " + path);
            } catch (Throwable e) {
                log("su 写入失败 " + path + ": " + e.getMessage());
            }
        }
    }

    // ---- 调试：返回原始存储内容 ----
    public static String rawStored() {
        return rawStored(null);
    }

    public static String rawStored(Context ctx) {
        StringBuilder sb = new StringBuilder();
        for (String path : LIC_PATHS) {
            try {
                File f = new File(path);
                if (!f.exists()) {
                    sb.append(path).append("(无) ");
                    continue;
                }
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line = reader.readLine();
                reader.close();
                sb.append(path).append(" => ").append(line).append(" ");
            } catch (Throwable e) {
                sb.append(path).append("(err) ");
            }
        }
        // 内部存储
        if (ctx != null) {
            try {
                File f = new File(ctx.getFilesDir(), "wklic.dat");
                if (f.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    String line = reader.readLine();
                    reader.close();
                    sb.append("[内部] => ").append(line);
                } else {
                    sb.append("[内部](无)");
                }
            } catch (Throwable e) {
                sb.append("[内部](err)");
            }
        }
        return sb.toString().trim();
    }

    // ---- 反调试检测 ----
    public static boolean isDebuggerConnected() {
        try {
            // 检测 JDWP 调试器
            if (android.os.Debug.isDebuggerConnected()) return true;
            // 检测是否被 waitForDebugger
            if (android.os.Debug.waitingForDebugger()) return true;
        } catch (Throwable ignore) {}
        try {
            // 检测 /proc/self/status 中的 TracerPid
            BufferedReader r = new BufferedReader(new FileReader("/proc/self/status"));
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("TracerPid:")) {
                    r.close();
                    String pid = line.substring(10).trim();
                    return !"0".equals(pid);
                }
            }
            r.close();
        } catch (Throwable ignore) {}
        return false;
    }

    // ---- 检测 su 是否可用 ----
    public static boolean isRootAvailable() {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("id\n");
            os.writeBytes("exit\n");
            os.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String out = reader.readLine();
            reader.close();
            p.waitFor();
            return out != null && out.contains("uid=0");
        } catch (Throwable e) {
            return false;
        }
    }

    // ---- 清除授权 ----
    public static void clear(Context ctx) {
        // v15: 同步清 SharedPreferences,闸门下次 onResume 才会重新弹
        if (ctx != null) {
            try {
                ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        .edit().clear().apply();
                log("SharedPreferences 已清除");
            } catch (Throwable e) {
                log("清 SharedPreferences 失败: " + e.getMessage());
            }
        }
        execSu("setprop " + PROP_KEY + " ''");
        for (String path : LIC_PATHS) {
            execSu("rm -f " + path);
            try { new File(path).delete(); } catch (Throwable ignore) {}
        }
    }

    private static void log(String msg) {
        try {
            android.util.Log.d("[KeytLicense]", msg);
        } catch (Throwable ignore) {}
    }
}
