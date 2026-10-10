package com.wkvip.module;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class WkVip implements IXposedHookLoadPackage {
    private static final String CLS_ENTRY = "n6.n";
    private static final String CLS_VIP = "O0.b";
    private static final String TAG = "[WKVip]";
    private static final String TARGET_PKG = "info.red.virtual";
    private static volatile boolean sLicensed = false;
    private static volatile ClassLoader sBaseLoader = null;
    private static volatile Activity sHostActivity = null;
    private static volatile Activity sGateOwner = null;
    private static volatile View sGateView = null;
    private static volatile boolean sPolling = false;
    private static final Set<Class<?>> HOOKED = Collections.newSetFromMap(new WeakHashMap());
    private static final List<XC_MethodHook.Unhook> UNHOOKS = Collections.synchronizedList(new ArrayList());

    private View buildGoToAuthView(final Activity activity) {
        int dp = dp(activity, 24);
        int dp2 = dp(activity, 20);
        int dp3 = dp(activity, 14);
        int dp4 = dp(activity, 10);
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setBackgroundColor(-872415232);
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        LinearLayout linearLayout2 = new LinearLayout(activity);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(17);
        linearLayout2.setBackgroundColor(-1);
        linearLayout2.setPadding(dp, dp2, dp, dp);
        linearLayout.addView(linearLayout2, new LinearLayout.LayoutParams(Math.min(dp(activity, 320), Math.max(dp(activity, 260), 0)), -2));
        TextView textView = new TextView(activity);
        textView.setText("需要授权");
        textView.setTextColor(-16777216);
        textView.setTextSize(20.0f);
        textView.setGravity(17);
        linearLayout2.addView(textView, new LinearLayout.LayoutParams(-1, -2));
        TextView textView2 = new TextView(activity);
        textView2.setText("请打开「WK授权」应用，\n输入卡密后即可解锁悟空分身 VIP。");
        textView2.setTextColor(-12303292);
        textView2.setTextSize(14.0f);
        textView2.setGravity(17);
        textView2.setPadding(0, dp3, 0, dp3);
        linearLayout2.addView(textView2, new LinearLayout.LayoutParams(-1, -2));
        LinearLayout linearLayout3 = new LinearLayout(activity);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(17);
        TextView textView3 = new TextView(activity);
        textView3.setText("打开 WK授权");
        textView3.setTextColor(-1);
        textView3.setTextSize(16.0f);
        textView3.setGravity(17);
        textView3.setBackgroundColor(Color.rgb(38, 50, 56));
        linearLayout3.addView(textView3, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView4 = new TextView(activity);
        textView4.setText("稍  后");
        textView4.setTextColor(-1);
        textView4.setTextSize(16.0f);
        textView4.setGravity(17);
        textView4.setBackgroundColor(Color.rgb(140, 140, 140));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
        layoutParams.setMarginStart(dp4);
        linearLayout3.addView(textView4, layoutParams);
        linearLayout2.addView(linearLayout3, new LinearLayout.LayoutParams(-1, -2, 0.0f));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.wkvip.module.WkVip.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Intent launchIntentForPackage = activity.getPackageManager().getLaunchIntentForPackage("com.wkvip.module");
                    if (launchIntentForPackage != null) {
                        launchIntentForPackage.addFlags(268435456);
                        activity.startActivity(launchIntentForPackage);
                        WkVip.log("已跳转至 WK授权 App");
                    } else {
                        WkVip.toast(activity, "未找到 WK授权 应用，请先安装");
                    }
                } catch (Throwable th) {
                    WkVip.toast(activity, "打开 WK授权 失败: " + th.getMessage());
                    WkVip.log("跳转 WK授权 异常: " + th);
                }
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.wkvip.module.WkVip.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WkVip.this.removeGate();
            }
        });
        return linearLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void disableBypass() {
        sLicensed = false;
        sPolling = false;
        List<XC_MethodHook.Unhook> list = UNHOOKS;
        synchronized (list) {
            Iterator<XC_MethodHook.Unhook> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().unhook();
                } catch (Throwable th) {
                }
            }
            UNHOOKS.clear();
        }
        HOOKED.clear();
        final View view = sGateView;
        sGateView = null;
        sGateOwner = null;
        if (view != null && (view.getParent() instanceof ViewGroup)) {
            final ViewGroup viewGroup = (ViewGroup) view.getParent();
            Activity activity = sHostActivity;
            if (activity != null) {
                try {
                    activity.runOnUiThread(new Runnable() { // from class: com.wkvip.module.WkVip.13
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                viewGroup.removeView(view);
                            } catch (Throwable th2) {
                            }
                        }
                    });
                } catch (Throwable th2) {
                }
            }
        }
        log("已撤销所有 VIP hook (卡密过期/吊销) -> VIP 立即失效");
    }

    private static int dp(Activity activity, int i) {
        try {
            return (int) ((i * activity.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Throwable th) {
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableBypass(Activity activity) {
        sLicensed = true;
        removeGate();
        if (sBaseLoader != null) {
            tryInstall(sBaseLoader);
            startPolling(sBaseLoader);
        }
        log("授权通过 -> 开始装配 VIP 绕过 hook");
    }

    private void hookNewClassLoaders() {
        try {
            XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", new Object[]{String.class, Boolean.TYPE, new XC_MethodHook() { // from class: com.wkvip.module.WkVip.9
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (WkVip.sLicensed) {
                        Object result = methodHookParam.getResult();
                        if (result instanceof Class) {
                            Class cls = (Class) result;
                            String name = cls.getName();
                            if (WkVip.CLS_ENTRY.equals(name)) {
                                WkVip.this.installEntryGate(cls);
                            } else if (WkVip.CLS_VIP.equals(name)) {
                                WkVip.this.installVipLevel(cls);
                            }
                            if (WkVip.HOOKED.size() >= 1) {
                                try {
                                    XposedBridge.unhookMethod(XposedHelpers.findMethodExact(ClassLoader.class, "loadClass", new Class[]{String.class, Boolean.TYPE}), this);
                                    WkVip.log("已卸载动态 ClassLoader 覆盖钩子");
                                } catch (Throwable th) {
                                }
                            }
                        }
                    }
                }
            }});
            log("已安装动态 ClassLoader 覆盖钩子");
        } catch (Throwable th) {
            log("安装 ClassLoader 钩子失败: " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void installEntryGate(Class<?> cls) {
        Set<Class<?>> set = HOOKED;
        if (set.contains(cls)) {
            return;
        }
        try {
            UNHOOKS.add(XposedHelpers.findAndHookMethod(cls, "a", new Object[]{String.class, new XC_MethodHook() { // from class: com.wkvip.module.WkVip.10
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    WkVip.log("n6.n.a(\"" + ((methodHookParam.args == null || methodHookParam.args.length <= 0) ? "?" : methodHookParam.args[0]) + "\") -> BYPASS(true)");
                    methodHookParam.setResult(Boolean.TRUE);
                }
            }}));
            set.add(cls);
            log("已安装入口 VIP 判定绕过 hook: " + cls.getName() + " (loader=" + cls.getClassLoader() + ")");
        } catch (Throwable th) {
            log("安装 n6.n.a hook 失败: " + th);
            try {
                int i = 0;
                for (Method method : cls.getDeclaredMethods()) {
                    if (method.getName().equals("a")) {
                        UNHOOKS.add(XposedBridge.hookMethod(method, new XC_MethodHook() { // from class: com.wkvip.module.WkVip.11
                            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                                methodHookParam.setResult(Boolean.TRUE);
                            }
                        }));
                        i++;
                    }
                }
                if (i > 0) {
                    HOOKED.add(cls);
                    log("已安装 n6.n.a x" + i + " (枚举兜底)");
                }
            } catch (Throwable th2) {
                log("枚举安装 n6.n 失败: " + th2);
            }
        }
    }

    private void installLicenseGate() {
        try {
            XposedHelpers.findAndHookMethod(Activity.class, "onResume", new Object[]{new XC_MethodHook() { // from class: com.wkvip.module.WkVip.1
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (!WkVip.sLicensed && (methodHookParam.thisObject instanceof Activity)) {
                        final Activity activity = (Activity) methodHookParam.thisObject;
                        if (WkVip.TARGET_PKG.equals(activity.getPackageName()) && !activity.isFinishing()) {
                            Activity unused = WkVip.sHostActivity = activity;
                            if (WkVip.sGateView != null) {
                                return;
                            }
                            activity.runOnUiThread(new Runnable() { // from class: com.wkvip.module.WkVip.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    WkVip.this.showGate(activity);
                                }
                            });
                        }
                    }
                }
            }});
            XposedHelpers.findAndHookMethod(Activity.class, "onWindowFocusChanged", new Object[]{Boolean.TYPE, new XC_MethodHook() { // from class: com.wkvip.module.WkVip.2
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (!WkVip.sLicensed && (methodHookParam.thisObject instanceof Activity)) {
                        final Activity activity = (Activity) methodHookParam.thisObject;
                        if (WkVip.TARGET_PKG.equals(activity.getPackageName())) {
                            if (!((Boolean) methodHookParam.args[0]).booleanValue()) {
                                if (WkVip.sGateOwner == activity) {
                                    WkVip.this.removeGate();
                                }
                            } else {
                                Activity unused = WkVip.sHostActivity = activity;
                                if (WkVip.sGateView == null) {
                                    activity.runOnUiThread(new Runnable() { // from class: com.wkvip.module.WkVip.2.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            WkVip.this.showGate(activity);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }});
            XposedHelpers.findAndHookMethod(Activity.class, "onDestroy", new Object[]{new XC_MethodHook() { // from class: com.wkvip.module.WkVip.3
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (methodHookParam.thisObject == WkVip.sGateOwner) {
                        WkVip.this.removeGate();
                    }
                }
            }});
            log("已安装卡密授权闸门 (DecorView 全屏覆盖层, v15)");
        } catch (Throwable th) {
            log("安装卡密闸门失败: " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void installVipLevel(Class<?> cls) {
        Set<Class<?>> set = HOOKED;
        if (set.contains(cls)) {
            return;
        }
        try {
            UNHOOKS.add(XposedBridge.hookMethod(XposedHelpers.findMethodExact(cls, "z", new Class[0]), new XC_MethodHook() { // from class: com.wkvip.module.WkVip.12
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Object result = methodHookParam.getResult();
                    if ((result instanceof Integer) && ((Integer) result).intValue() == 0) {
                        methodHookParam.setResult(1);
                    }
                }
            }));
            set.add(cls);
            log("已安装会员等级兜底 hook: O0.b.z()");
        } catch (Throwable th) {
            log("安装 O0.b.z hook 失败: " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void log(String str) {
        try {
            XposedBridge.log("[WKVip] " + str);
        } catch (Throwable th) {
        }
    }

    private String[] queryProviderCard(Activity activity) {
        Cursor cursor;
        if (activity == null) {
            return null;
        }
        try {
            cursor = activity.getContentResolver().query(Uri.parse("content://com.wkvip.lic/license"), null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex("card");
                        int columnIndex2 = cursor.getColumnIndex("expire");
                        int columnIndex3 = cursor.getColumnIndex("type");
                        String str = "";
                        String string = columnIndex >= 0 ? cursor.getString(columnIndex) : "";
                        String valueOf = columnIndex2 >= 0 ? String.valueOf(cursor.getLong(columnIndex2)) : "0";
                        String str2 = "yearly";
                        String string2 = columnIndex3 >= 0 ? cursor.getString(columnIndex3) : "yearly";
                        if (string != null) {
                            str = string;
                        }
                        if (string2 != null) {
                            str2 = string2;
                        }
                        log("provider 返回 card=" + str + " expire=" + valueOf + " type=" + str2);
                        return new String[]{str, valueOf, str2};
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        log("queryProviderCard 异常: " + th);
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Throwable th2) {
                            }
                        }
                        return null;
                    } finally {
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Throwable th3) {
                            }
                        }
                    }
                }
            }
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Throwable th4) {
                }
            }
            return null;
        } catch (Throwable th5) {
            th = th5;
            cursor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean recheckOnline(Activity activity, boolean z) {
        if (activity != null && KeytLicense.isPermanentCard(activity)) {
            log("永久卡,跳过联网复验");
            return true;
        }
        if (activity == null) {
            if (!z) {
                return true;
            }
            disableBypass();
            return false;
        }
        String cardFromPrefs = KeytLicense.cardFromPrefs(activity);
        if (cardFromPrefs == null || cardFromPrefs.isEmpty()) {
            cardFromPrefs = KeytLicense.storedCard(activity);
        }
        if (cardFromPrefs == null || cardFromPrefs.isEmpty()) {
            if (!z) {
                return true;
            }
            disableBypass();
            return false;
        }
        String activate = KeytLicense.activate(activity, cardFromPrefs);
        if (activate == null) {
            log("联网复验成功(卡密续期/有效),保持 VIP");
            return true;
        }
        if (activate.contains("网络") || activate.contains("响应") || activate.contains("频繁")) {
            log("联网复验临时错误,保留 VIP 等待重试: " + activate);
            return true;
        }
        log("卡密失效: " + activate + " -> 撤销 hook,VIP 关闭");
        disableBypass();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeGate() {
        try {
            View view = sGateView;
            sGateView = null;
            sGateOwner = null;
            if (view == null || !(view.getParent() instanceof ViewGroup)) {
                return;
            }
            ((ViewGroup) view.getParent()).removeView(view);
        } catch (Throwable th) {
            log("removeGate err: " + th);
        }
    }

    private void retryGate(final Activity activity) {
        if (sLicensed) {
            return;
        }
        try {
            new Handler(activity.getMainLooper()).postDelayed(new Runnable() { // from class: com.wkvip.module.WkVip.5
                @Override // java.lang.Runnable
                public void run() {
                    WkVip.this.showGate(activity);
                }
            }, 400L);
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGate(final Activity activity) {
        String str;
        long j;
        if (sLicensed || sGateView != null) {
            return;
        }
        if (KeytLicense.isValidFromPrefs(activity)) {
            log("本地 prefs 授权有效(card=" + KeytLicense.cardFromPrefs(activity) + ", permanent=" + KeytLicense.isPermanentCard(activity) + ") -> 直接装配,不弹闸门");
            enableBypass(activity);
            return;
        }
        String[] queryProviderCard = queryProviderCard(activity);
        if (queryProviderCard == null || (str = queryProviderCard[0]) == null || str.isEmpty()) {
            if (!KeytLicense.isValidQuick()) {
                showGoToAuthOverlay(activity);
                return;
            } else {
                log("文件授权仍然有效 -> 直接装配,不弹闸门");
                enableBypass(activity);
                return;
            }
        }
        final String str2 = queryProviderCard[0];
        try {
            j = Long.parseLong(queryProviderCard[1]);
        } catch (Throwable th) {
            j = 0;
        }
        String str3 = queryProviderCard.length >= 3 ? queryProviderCard[2] : "yearly";
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if ("permanent".equalsIgnoreCase(str3) || KeytLicense.isPermanentExpire(j)) {
            KeytLicense.mirror(activity, str2, j);
            log("provider 永久卡(card=" + str2 + ") -> 镜像 prefs 并装配,不弹闸门,不联网");
            enableBypass(activity);
        } else if (j <= 0 || currentTimeMillis >= j) {
            log("provider 年卡已过期,后台联网复验 card=" + str2);
            showVerifyingOverlay(activity, "正在验证授权…");
            new Thread(new Runnable() { // from class: com.wkvip.module.WkVip.4
                @Override // java.lang.Runnable
                public void run() {
                    final String activate = KeytLicense.activate(activity, str2);
                    activity.runOnUiThread(new Runnable() { // from class: com.wkvip.module.WkVip.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WkVip.this.removeGate();
                            if (activate == null) {
                                WkVip.this.enableBypass(activity);
                            } else {
                                WkVip.log("年卡自动复验失败: " + activate + " -> 提示去WK授权");
                                WkVip.this.showGoToAuthOverlay(activity);
                            }
                        }
                    });
                }
            }, "wklic-auto").start();
        } else {
            KeytLicense.mirror(activity, str2, j);
            log("provider 年卡有效(card=" + str2 + ",expire=" + j + ") -> 镜像 prefs 并装配,不弹闸门");
            enableBypass(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGoToAuthOverlay(Activity activity) {
        if (sLicensed || sGateView != null) {
            return;
        }
        try {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                Window window = activity.getWindow();
                if (window == null) {
                    retryGate(activity);
                    return;
                }
                View decorView = window.getDecorView();
                if (decorView != null && (decorView instanceof ViewGroup)) {
                    View buildGoToAuthView = buildGoToAuthView(activity);
                    ((ViewGroup) decorView).addView(buildGoToAuthView, new ViewGroup.LayoutParams(-1, -1));
                    sGateView = buildGoToAuthView;
                    sGateOwner = activity;
                    log("已挂载「去WK授权」提示覆盖层于 " + activity.getClass().getSimpleName());
                    return;
                }
                log("showGoToAuthOverlay: 无 DecorView, 稍后重试");
                retryGate(activity);
                return;
            }
            retryGate(activity);
        } catch (Throwable th) {
            log("挂载「去WK授权」覆盖层失败: " + th);
            sGateView = null;
            sGateOwner = null;
            retryGate(activity);
        }
    }

    private void showVerifyingOverlay(Activity activity, String str) {
        Window window;
        try {
            if (activity.isFinishing() || activity.isDestroyed() || (window = activity.getWindow()) == null) {
                return;
            }
            View decorView = window.getDecorView();
            if (decorView instanceof ViewGroup) {
                TextView textView = new TextView(activity);
                textView.setText(str);
                textView.setTextColor(-1);
                textView.setGravity(17);
                textView.setBackgroundColor(-872415232);
                textView.setPadding(dp(activity, 40), dp(activity, 24), dp(activity, 40), dp(activity, 24));
                ((ViewGroup) decorView).addView(textView, new ViewGroup.LayoutParams(-1, -1));
                sGateView = textView;
                sGateOwner = activity;
            }
        } catch (Throwable th) {
            log("showVerifyingOverlay 异常: " + th);
        }
    }

    private void startPolling(ClassLoader classLoader) {
        if (!sLicensed || sPolling) {
            return;
        }
        sPolling = true;
        new Thread(new Runnable() { // from class: com.wkvip.module.WkVip.14
            @Override // java.lang.Runnable
            public void run() {
                int i = 0;
                while (WkVip.sPolling && WkVip.sLicensed) {
                    try {
                        Thread.sleep(60000L);
                    } catch (Throwable th) {
                        WkVip.log("polling 异常: " + th);
                    }
                    if (!WkVip.sLicensed) {
                        break;
                    }
                    Activity activity = WkVip.sHostActivity;
                    i++;
                    if (activity == null || !KeytLicense.isPermanentCard(activity)) {
                        boolean isValidFromPrefs = activity != null ? KeytLicense.isValidFromPrefs(activity) : false;
                        if (!isValidFromPrefs) {
                            isValidFromPrefs = KeytLicense.isValidQuick();
                        }
                        if (!isValidFromPrefs) {
                            WkVip.log("年卡本地授权已过期,联网复验...");
                            if (!WkVip.this.recheckOnline(activity, true)) {
                                break;
                            }
                        } else if (i % 60 == 0) {
                            WkVip.log("年卡周期性联网复验(检测吊销)...");
                            if (!WkVip.this.recheckOnline(activity, false)) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else if (!KeytLicense.isValidFromPrefs(activity)) {
                        WkVip.log("永久卡指纹不匹配(换设备) -> 撤销 hook");
                        WkVip.this.disableBypass();
                        break;
                    }
                }
                boolean unused = WkVip.sPolling = false;
            }
        }, "wkvip-poll").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void toast(final Activity activity, final String str) {
        try {
            activity.runOnUiThread(new Runnable() { // from class: com.wkvip.module.WkVip.8
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(activity, str, 0).show();
                }
            });
        } catch (Throwable th) {
        }
    }

    private void tryInstall(ClassLoader classLoader) {
        if (!sLicensed || classLoader == null) {
            return;
        }
        try {
            Class<?> findClassIfExists = XposedHelpers.findClassIfExists(CLS_ENTRY, classLoader);
            if (findClassIfExists != null) {
                installEntryGate(findClassIfExists);
            }
        } catch (Throwable th) {
            log("tryInstall n6.n 失败: " + th);
        }
        try {
            Class<?> findClassIfExists2 = XposedHelpers.findClassIfExists(CLS_VIP, classLoader);
            if (findClassIfExists2 != null) {
                installVipLevel(findClassIfExists2);
            }
        } catch (Throwable th2) {
            log("tryInstall O0.b 失败: " + th2);
        }
    }

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (TARGET_PKG.equals(loadPackageParam.packageName)) {
            log("加载目标包: " + loadPackageParam.packageName + " process=" + loadPackageParam.processName);
            sBaseLoader = loadPackageParam.classLoader;
            installLicenseGate();
            hookNewClassLoaders();
        }
    }
}
