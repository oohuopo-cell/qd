package com.wkvip.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class KeytLicense {
    private static final String APP_NAME = "wk";
    private static final String BASE_URL = "https://www.keyt.cn/kami/a1165707085/check.php";
    private static final String K_CARD = "card";
    private static final String K_EXPIRE = "expire_at";
    private static final String K_FP = "fp";
    private static final String K_TYPE = "type";
    private static final String[] LIC_PATHS = {"/data/local/tmp/wklic.dat", "/sdcard/wklic.dat"};
    private static final long PERMANENT_THRESHOLD = 315360000;
    private static final String PREFS_NAME = "wklic_store";
    private static final String PROP_KEY = "persist.wk.lic";
    private static final String SEP = "::";
    private static final String SIGN_KEY = "9107e61c0f4ae987218827ef8c78cbeb";
    private static final String TAG = "[KeytLicense]";
    private static final int TIMEOUT_MS = 15000;
    private static final int TIMESTAMP_MAX_DIFF = 120;
    private static final String TYPE_PERMANENT = "permanent";
    private static final String TYPE_YEARLY = "yearly";
    private static final String USER_NAME = "a1165707085";

    private KeytLicense() {
    }

    public static String activate(Context context, String str) {
        long j;
        if (str == null || str.trim().isEmpty()) {
            return "请输入卡密";
        }
        String upperCase = str.trim().toUpperCase();
        log("======= 开始联网验证 =======");
        log("卡密: " + upperCase);
        String machineCode = getMachineCode(context);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String format = simpleDateFormat.format(new Date());
        log("机器码: " + machineCode);
        log("时间戳(t): " + format);
        String str2 = "https://www.keyt.cn/kami/a1165707085/check.php?card=" + upperCase + "&mac=" + machineCode + "&app=" + APP_NAME + "&heart=1&t=" + format;
        log("请求URL: " + str2);
        String httpGet = httpGet(str2);
        if (httpGet == null || httpGet.isEmpty()) {
            return "服务器无响应，请检查网络";
        }
        log("服务器原始返回: [" + httpGet + "]");
        String verifyResponse = verifyResponse(httpGet);
        if (verifyResponse == null) {
            String trim = httpGet.toLowerCase().trim();
            log("验签失败，检查纯文本响应: " + trim);
            if (trim.contains("too_frequent") || trim.contains("frequent")) {
                return "请求过于频繁，请稍等1分钟再试";
            }
            if (!trim.equals("activate") && !trim.equals("valide") && !trim.equals("valid") && !trim.equals("success") && !trim.equals("ok") && !trim.equals("1") && !trim.equals("true")) {
                return "验证失败: " + httpGet;
            }
            log("纯文本成功响应: " + httpGet);
            saveLicense(context, upperCase, (System.currentTimeMillis() / 1000) + 31536000);
            return null;
        }
        log("解析业务数据: " + verifyResponse);
        if (!verifyResponse.startsWith("ok|")) {
            if (!verifyResponse.startsWith("error|")) {
                log("未知业务数据格式: " + verifyResponse);
                return "验证失败: " + verifyResponse;
            }
            String[] split = verifyResponse.split("\\|");
            String trim2 = split.length >= 2 ? split[1].trim() : "unknown";
            String transMsg = transMsg(trim2);
            log("❌ 验证失败: " + trim2 + " → " + transMsg);
            return transMsg;
        }
        log("✅ 验证成功!");
        String[] split2 = verifyResponse.split("\\|");
        log("biz 分段数: " + split2.length);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (split2.length >= 3) {
            try {
                int parseInt = Integer.parseInt(split2[2].trim());
                int parseInt2 = split2.length >= 4 ? Integer.parseInt(split2[3].trim()) : 0;
                if (parseInt2 >= 1440) {
                    log("剩余: " + parseInt2 + " 分钟 (总分钟数)");
                    j = (parseInt2 * 60) + currentTimeMillis;
                } else {
                    log("剩余: " + parseInt + "天 " + parseInt2 + "分钟");
                    j = (parseInt * 86400) + currentTimeMillis + (parseInt2 * 60);
                }
            } catch (NumberFormatException e) {
                log("解析天数失败，parts[2]=" + split2[2]);
                j = 31536000 + currentTimeMillis;
            }
        } else if (split2.length < 2) {
            log("biz 格式异常，默认365天");
            j = 31536000 + currentTimeMillis;
        } else if (TYPE_PERMANENT.equalsIgnoreCase(split2[1].trim()) || "forever".equalsIgnoreCase(split2[1].trim())) {
            log("永久卡密");
            j = 3153600000L + currentTimeMillis;
        } else {
            log("无天数信息，parts[1]=" + split2[1] + "，默认365天");
            j = 31536000 + currentTimeMillis;
        }
        saveLicense(context, upperCase, j);
        log("授权已保存, card=" + upperCase + " expire=" + j + " (距今 " + ((j - currentTimeMillis) / 86400) + " 天)");
        return null;
    }

    public static String cardFromPrefs(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getSharedPreferences(PREFS_NAME, 0).getString(K_CARD, "");
        } catch (Throwable th) {
            return "";
        }
    }

    public static void clear(Context context) {
        if (context != null) {
            try {
                context.getSharedPreferences(PREFS_NAME, 0).edit().clear().apply();
                log("SharedPreferences 已清除");
            } catch (Throwable th) {
                log("清 SharedPreferences 失败: " + th.getMessage());
            }
        }
        execSu("setprop persist.wk.lic ''");
        for (String str : LIC_PATHS) {
            execSu("rm -f " + str);
            try {
                new File(str).delete();
            } catch (Throwable th2) {
            }
        }
    }

    private static String execSu(String... strArr) {
        try {
            Process exec = Runtime.getRuntime().exec("su");
            DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
            for (String str : strArr) {
                dataOutputStream.writeBytes(str + "\n");
            }
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            bufferedReader.close();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            StringBuilder sb2 = new StringBuilder();
            while (true) {
                String readLine2 = bufferedReader2.readLine();
                if (readLine2 == null) {
                    break;
                }
                sb2.append(readLine2);
            }
            bufferedReader2.close();
            exec.waitFor();
            if (sb2.length() > 0) {
                log("su stderr: " + ((Object) sb2));
            }
            return sb.toString();
        } catch (Throwable th) {
            log("execSu 异常: " + th);
            return "";
        }
    }

    public static long expireAt(Context context) {
        String readLicense = readLicense(false, context);
        if (readLicense == null || readLicense.isEmpty()) {
            return 0L;
        }
        String[] split = readLicense.split(SEP);
        if (split.length < 2) {
            split = readLicense.split("\\|");
        }
        if (split.length >= 2) {
            try {
                return Long.parseLong(split[1].trim());
            } catch (Throwable th) {
            }
        }
        return 0L;
    }

    public static long expireFromPrefs(Context context) {
        if (context == null) {
            return 0L;
        }
        try {
            return context.getSharedPreferences(PREFS_NAME, 0).getLong(K_EXPIRE, 0L);
        } catch (Throwable th) {
            return 0L;
        }
    }

    public static String getMachineCode(Context context) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest((Build.BRAND + Build.MODEL + Build.DEVICE + Build.PRODUCT + Build.MANUFACTURER + Build.FINGERPRINT + Build.VERSION.RELEASE).getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", Integer.valueOf(b & 255)));
            }
            return sb.toString().substring(0, 16);
        } catch (Throwable th) {
            return "UNKNOWN0000000000";
        }
    }

    private static String getSystemProperty(String str) {
        try {
            String str2 = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
            if (str2 == null || str2.isEmpty()) {
                return "";
            }
            log("getprop " + str + " = " + str2);
            return str2;
        } catch (Throwable th) {
            log("SystemProperties 反射失败: " + th);
            return "";
        }
    }

    private static String httpGet(String str) {
        Object th;
        HttpURLConnection httpURLConnection;
        int responseCode;
        try {
            try {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(TIMEOUT_MS);
                    httpURLConnection.setReadTimeout(TIMEOUT_MS);
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + ")");
                    httpURLConnection.setRequestProperty("Accept", "*/*");
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                    responseCode = httpURLConnection.getResponseCode();
                    log("HTTP 响应码: " + responseCode);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        log("httpGet 异常: " + th);
                        if (httpURLConnection == null) {
                            return "";
                        }
                        httpURLConnection.disconnect();
                        return "";
                    } finally {
                        if (httpURLConnection != null) {
                            try {
                                httpURLConnection.disconnect();
                            } catch (Throwable th3) {
                            }
                        }
                    }
                }
            } catch (Throwable th4) {
                return "";
            }
        } catch (Throwable th5) {
            th = th5;
            httpURLConnection = null;
        }
        if (responseCode != 200) {
            if (httpURLConnection == null) {
                return "";
            }
            httpURLConnection.disconnect();
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            sb.append(readLine);
        }
        bufferedReader.close();
        return sb.toString();
    }

    public static boolean isDebuggerConnected() {
        String readLine;
        if (Debug.isDebuggerConnected()) {
            return true;
        }
        if (Debug.waitingForDebugger()) {
            return true;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/self/status"));
            do {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return false;
                }
            } while (!readLine.startsWith("TracerPid:"));
            bufferedReader.close();
            return true ^ "0".equals(readLine.substring(10).trim());
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isPermanentCard(Context context) {
        if (context == null) {
            return false;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
            if (TYPE_PERMANENT.equals(sharedPreferences.getString(K_TYPE, ""))) {
                return true;
            }
            return sharedPreferences.getLong(K_EXPIRE, 0L) - (System.currentTimeMillis() / 1000) > PERMANENT_THRESHOLD;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isPermanentExpire(long j) {
        return j > 0 && j - (System.currentTimeMillis() / 1000) > PERMANENT_THRESHOLD;
    }

    public static boolean isRootAvailable() {
        try {
            Process exec = Runtime.getRuntime().exec("su");
            DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
            dataOutputStream.writeBytes("id\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            exec.waitFor();
            if (readLine != null) {
                return readLine.contains("uid=0");
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isValid(Context context) {
        String trim;
        long j;
        try {
            String readLicense = readLicense(false, context);
            if (readLicense != null && !readLicense.isEmpty()) {
                String[] split = readLicense.split(SEP);
                if (split.length < 2 || (trim = split[0].trim()) == null || trim.isEmpty()) {
                    return false;
                }
                try {
                    j = Long.parseLong(split[1].trim());
                } catch (Throwable th) {
                    j = 0;
                }
                if (split.length < 3) {
                    return false;
                }
                if (!md5(trim + j + SIGN_KEY).equalsIgnoreCase(split[2].trim())) {
                    log("签名不匹配，授权文件可能被篡改");
                    return false;
                }
                if (j > 0) {
                    long currentTimeMillis = System.currentTimeMillis() / 1000;
                    if (currentTimeMillis > j) {
                        log("卡密已过期: expire=" + j + " now=" + currentTimeMillis);
                        return false;
                    }
                }
                log("授权有效: card=" + trim);
                return true;
            }
            log("未找到有效授权");
            return false;
        } catch (Throwable th2) {
            log("isValid 异常: " + th2);
            return false;
        }
    }

    public static boolean isValidFromPrefs(Context context) {
        if (context == null) {
            return false;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
            String string = sharedPreferences.getString(K_CARD, "");
            if (string != null && !string.isEmpty()) {
                if (!getMachineCode(context).equals(sharedPreferences.getString(K_FP, ""))) {
                    log("prefs 指纹不匹配,授权作废");
                    return false;
                }
                if (isPermanentCard(context)) {
                    log("prefs 永久卡授权有效(card=" + string + ") -> 离线放行");
                    return true;
                }
                long j = sharedPreferences.getLong(K_EXPIRE, 0L);
                if (j <= 0) {
                    return false;
                }
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                if (currentTimeMillis > j) {
                    log("prefs 年卡已过期: expire=" + j + " now=" + currentTimeMillis);
                    return false;
                }
                log("prefs 年卡授权有效(card=" + string + ")");
                return true;
            }
            return false;
        } catch (Throwable th) {
            log("isValidFromPrefs 异常: " + th);
            return false;
        }
    }

    public static boolean isValidQuick() {
        long j;
        try {
            String readLicense = readLicense(true);
            if (readLicense != null && !readLicense.isEmpty()) {
                String[] split = readLicense.split(SEP);
                if (split.length < 2) {
                    split = readLicense.split("\\|");
                }
                if (split.length < 2) {
                    return false;
                }
                String trim = split[0].trim();
                try {
                    j = Long.parseLong(split[1].trim());
                } catch (Throwable th) {
                    j = 0;
                }
                if (split.length < 3) {
                    return false;
                }
                if (!md5(trim + j + SIGN_KEY).equalsIgnoreCase(split[2].trim())) {
                    log("签名不匹配，授权文件可能被篡改");
                    return false;
                }
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                if (j - currentTimeMillis <= PERMANENT_THRESHOLD) {
                    return ((j > 0 && currentTimeMillis > j) || trim == null || trim.isEmpty()) ? false : true;
                }
                log("文件永久卡授权有效(card=" + trim + ") -> 离线放行");
                return (trim == null || trim.isEmpty()) ? false : true;
            }
            return false;
        } catch (Throwable th2) {
            return false;
        }
    }

    private static void log(String str) {
        try {
            Log.d(TAG, str);
        } catch (Throwable th) {
        }
    }

    private static String md5(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", Integer.valueOf(b & 255)));
            }
            return sb.toString();
        } catch (Throwable th) {
            return "";
        }
    }

    public static void mirror(Context context, String str, long j) {
        saveToPrefs(context, str, j);
    }

    public static String rawStored() {
        return rawStored(null);
    }

    public static String rawStored(Context context) {
        StringBuilder sb = new StringBuilder();
        for (String str : LIC_PATHS) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    sb.append(str).append(" => ").append(readLine).append(" ");
                } else {
                    sb.append(str).append("(无) ");
                }
            } catch (Throwable th) {
                sb.append(str).append("(err) ");
            }
        }
        if (context != null) {
            try {
                File file2 = new File(context.getFilesDir(), "wklic.dat");
                if (file2.exists()) {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
                    String readLine2 = bufferedReader2.readLine();
                    bufferedReader2.close();
                    sb.append("[内部] => ").append(readLine2);
                } else {
                    sb.append("[内部](无)");
                }
            } catch (Throwable th2) {
                sb.append("[内部](err)");
            }
        }
        return sb.toString().trim();
    }

    private static String readLicense() {
        return readLicense(false);
    }

    private static String readLicense(boolean z) {
        return readLicense(z, null);
    }

    private static String readLicense(boolean z, Context context) {
        for (String str : LIC_PATHS) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    if (readLine != null && !readLine.isEmpty()) {
                        log("从文件读到 " + str + ": " + readLine);
                        return readLine;
                    }
                }
            } catch (Throwable th) {
                log("读文件失败 " + str + ": " + th.getMessage());
            }
        }
        if (context != null) {
            try {
                File file2 = new File(context.getFilesDir(), "wklic.dat");
                if (file2.exists()) {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
                    String readLine2 = bufferedReader2.readLine();
                    bufferedReader2.close();
                    if (readLine2 != null && !readLine2.isEmpty()) {
                        log("从内部存储读到: " + readLine2);
                        return readLine2;
                    }
                }
            } catch (Throwable th2) {
                log("读内部存储失败: " + th2.getMessage());
            }
        }
        if (z) {
            return null;
        }
        for (String str2 : LIC_PATHS) {
            String execSu = execSu("cat " + str2);
            if (execSu != null && !execSu.isEmpty()) {
                log("从 su 读到 " + str2 + ": " + execSu);
                return execSu;
            }
        }
        return null;
    }

    private static void saveLicense(Context context, String str, long j) {
        String str2 = str + SEP + j + SEP + md5(str + j + SIGN_KEY);
        log("保存授权数据: " + str2);
        saveToPrefs(context, str, j);
        String str3 = null;
        if (context != null) {
            try {
                File file = new File(context.getFilesDir(), "wklic.dat");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(str2);
                fileWriter.close();
                str3 = file.getAbsolutePath();
                log("内部存储写入成功: " + str3);
            } catch (Throwable th) {
                log("内部存储写入失败: " + th.getMessage());
            }
        }
        for (String str4 : LIC_PATHS) {
            try {
                File file2 = new File(str4);
                File parentFile = file2.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FileWriter fileWriter2 = new FileWriter(file2);
                fileWriter2.write(str2);
                fileWriter2.close();
                try {
                    Runtime.getRuntime().exec(new String[]{"chmod", "666", str4}).waitFor();
                } catch (Throwable th2) {
                }
                log("直接写入成功: " + str4);
            } catch (Throwable th3) {
                log("直接写入失败 " + str4 + ": " + th3.getMessage());
                if (str3 != null) {
                    try {
                        execSu("cp " + str3 + " " + str4, "chmod 666 " + str4);
                    } catch (Throwable th4) {
                        log("su 写入失败 " + str4 + ": " + th4.getMessage());
                    }
                } else {
                    execSu("printf '%s' '" + str2 + "' > " + str4, "chmod 666 " + str4);
                }
                log("su 写入完成: " + str4);
            }
        }
    }

    private static void saveToPrefs(Context context, String str, long j) {
        if (context == null) {
            return;
        }
        try {
            String machineCode = getMachineCode(context);
            String str2 = j - (System.currentTimeMillis() / 1000) > PERMANENT_THRESHOLD ? TYPE_PERMANENT : TYPE_YEARLY;
            context.getSharedPreferences(PREFS_NAME, 0).edit().putString(K_CARD, str).putLong(K_EXPIRE, j).putString(K_FP, machineCode).putString(K_TYPE, str2).apply();
            log("SharedPreferences 写入成功: card=" + str + " expire=" + j + " type=" + str2);
        } catch (Throwable th) {
            log("SharedPreferences 写入失败: " + th.getMessage());
        }
    }

    public static String storedCard(Context context) {
        String readLicense = readLicense(false, context);
        if (readLicense == null || readLicense.isEmpty()) {
            return "";
        }
        String[] split = readLicense.split(SEP);
        if (split.length < 1) {
            split = readLicense.split("\\|");
        }
        return split.length >= 1 ? split[0] : "";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static String transMsg(String str) {
        char c;
        if (str == null) {
            return "未知错误";
        }
        String lowerCase = str.toLowerCase();
        switch (lowerCase.hashCode()) {
            case -1705024744:
                if (lowerCase.equals("mac_error")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1655974669:
                if (lowerCase.equals("activate")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1578335911:
                if (lowerCase.equals("too_frequent")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1526279474:
                if (lowerCase.equals("frequent")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1396343010:
                if (lowerCase.equals("banned")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1309235419:
                if (lowerCase.equals("expired")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1186089319:
                if (lowerCase.equals("not_found_card")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -823824407:
                if (lowerCase.equals("valide")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3599293:
                if (lowerCase.equals("used")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 16868759:
                if (lowerCase.equals("device_mismatch")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 111972348:
                if (lowerCase.equals("valid")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 526705752:
                if (lowerCase.equals("invalid_card")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 668488878:
                if (lowerCase.equals(TYPE_PERMANENT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 980100121:
                if (lowerCase.equals("card_error")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1572853730:
                if (lowerCase.equals("online_limit_reached")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1615526678:
                if (lowerCase.equals("not_found")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "激活成功";
            case 1:
            case 2:
                return "验证通过";
            case 3:
                return "终身有效";
            case 4:
                return "卡密已过期";
            case 5:
                return "卡密已被禁用";
            case 6:
                return "设备不匹配（此卡密已绑定其他设备）";
            case 7:
                return "在线设备数已满";
            case '\b':
                return "卡密无效";
            case '\t':
                return "卡密不存在";
            case '\n':
                return "卡密不存在";
            case 11:
                return "卡密错误";
            case '\f':
                return "机器码不匹配";
            case '\r':
                return "卡密已被使用";
            case 14:
                return "请求过于频繁，请稍等1分钟再试";
            case 15:
                return "请求过于频繁，请稍等1分钟再试";
            default:
                return str;
        }
    }

    private static String verifyResponse(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String trim = str.trim();
        log("verifyResponse 原始: " + trim);
        int indexOf = trim.indexOf("|sign=");
        if (indexOf == -1) {
            log("未找到 |sign= 标记");
            return null;
        }
        String substring = trim.substring(0, indexOf);
        String substring2 = trim.substring(indexOf + 6);
        String md5 = md5(substring + SIGN_KEY);
        log("本地签名: " + md5);
        log("服务器签名: " + substring2);
        if (!md5.equals(substring2)) {
            log("签名不匹配");
            return null;
        }
        log("签名校验通过");
        int lastIndexOf = substring.lastIndexOf(124);
        if (lastIndexOf == -1) {
            log("未找到时间戳分隔符");
            return null;
        }
        String substring3 = substring.substring(lastIndexOf + 1);
        try {
            long parseLong = Long.parseLong(substring3.trim());
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            long abs = Math.abs(currentTimeMillis - parseLong);
            log("服务器时间戳: " + parseLong + " 本地: " + currentTimeMillis + " 差: " + abs + "秒");
            if (abs > 120) {
                log("时间戳偏差过大");
                return null;
            }
            log("时间戳校验通过");
            String substring4 = substring.substring(0, lastIndexOf);
            log("业务内容: " + substring4);
            return substring4;
        } catch (NumberFormatException e) {
            log("时间戳解析失败: " + substring3);
            return null;
        }
    }
}
