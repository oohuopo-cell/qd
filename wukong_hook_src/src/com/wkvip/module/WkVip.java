// =====================================================================
//  WKVip v15 —— 悟空分身 (info.red.virtual) VIP 解锁 (LSPosed 模块)
//
//  v15: 参考可用模块重写,修复"没有 hook"和"卡密验证不生效"两个问题
//
//  根因分析(旧版 v14 的两个致命缺陷):
//    A) 旧版只 hook ClassLoader.loadClass + 文件读卡密,但:
//       - 模块进程内没有 UI,用户无法在目标 App 内输入卡密
//       - /data/local/tmp/wklic.dat 需要 root 才能写,目标进程通常读不到
//       - sLicensed 永远为 false -> hook 装了也返回原值,看起来"没有 hook"
//    B) 旧版 checkLicense() 只读文件 + 检查 expire,完全不验签,
//       任何人手写一个 fake 文件就能绕过;同时 KeytLicense 写文件依赖 su,
//       无 root 时写不进去 -> 卡密验证功能形同虚设
//
//  v15 修复方案(对齐别人可用模块 WkVipHook):
//    1. handleLoadPackage 先装 Activity.onResume / onWindowFocusChanged /
//       onDestroy 钩子,在目标 App 的 DecorView 上挂全屏卡密闸门
//    2. 闸门内输入卡密 -> KeytLicense.activate() 联网验证
//       -> 成功后 saveLicense() 同时写 SharedPreferences(模块进程内可读)
//    3. enableBypass() 置 sLicensed=true、移除闸门、再装 n6.n / O0.b 钩子
//    4. VIP 钩子激活后 unconditional BYPASS(true) / 返回 1,
//       不再每次回头查文件,避免读权限导致"看似没生效"
//    5. ClassLoader.loadClass 钩子只在 sLicensed 后才捕类,捕到 n6.n 即卸载自己
// =====================================================================

package com.wkvip.module;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WkVip implements IXposedHookLoadPackage {

    private static final String TAG = "[WKVip]";
    private static final String TARGET_PKG = "info.red.virtual";
    private static final String CLS_ENTRY = "n6.n";   // 入口 VIP 判定
    private static final String CLS_VIP   = "O0.b";   // 会员等级

    // 已授权才装配 VIP 绕过钩子;未授权时闸门挡住,任何 VIP 接口原样返回
    private static volatile boolean sLicensed = false;

    // 装载机/闸门状态
    private static volatile ClassLoader sBaseLoader = null;
    private static volatile Activity sHostActivity = null;
    private static volatile Activity sGateOwner = null;
    private static volatile View sGateView = null;
    private static volatile boolean sPolling = false;

    // 已 hook 过的类(WeakHashMap,ClassLoader 被回收后自动清理)
    private static final Set<Class<?>> HOOKED =
            Collections.newSetFromMap(new WeakHashMap<Class<?>, Boolean>());

    // 已装配 hook 的卸载句柄(过期/吊销时遍历 unhook,真正关闭 VIP)
    // 旧版只改 sLicensed 标志位,hook 仍挂在 XposedBridge 上 -> VIP 照样生效,等于没失效
    private static final java.util.List<XC_MethodHook.Unhook> UNHOOKS =
            Collections.synchronizedList(new java.util.ArrayList<XC_MethodHook.Unhook>());

    private static void log(String msg) {
        try { XposedBridge.log(TAG + " " + msg); } catch (Throwable ignore) {}
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!TARGET_PKG.equals(lpparam.packageName)) return;
        log("加载目标包: " + lpparam.packageName + " process=" + lpparam.processName);

        sBaseLoader = lpparam.classLoader;

        // 1) 卡密授权闸门:在目标 App 的 Activity 生命周期上挂全屏覆盖层
        installLicenseGate();

        // 2) 动态 ClassLoader 覆盖钩子:授权后才捕 n6.n / O0.b
        hookNewClassLoaders();
    }

    // ============ 卡密闸门 ============
    private void installLicenseGate() {
        try {
            // Activity.onResume -> 首次/每次进前台时检查授权,无授权则挂闸门
            XposedHelpers.findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (sLicensed) return;
                    if (!(param.thisObject instanceof Activity)) return;
                    final Activity act = (Activity) param.thisObject;
                    if (!TARGET_PKG.equals(act.getPackageName())) return;
                    if (act.isFinishing()) return;
                    sHostActivity = act;
                    if (sGateView != null) return; // 闸门已挂
                    act.runOnUiThread(new Runnable() {
                        @Override public void run() { showGate(act); }
                    });
                }
            });

            // Activity.onWindowFocusChanged(true) -> 拿到焦点时补挂闸门
            // (有些 Activity onResume 时 DecorView 还没准备好)
            XposedHelpers.findAndHookMethod(Activity.class, "onWindowFocusChanged",
                    boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (sLicensed) return;
                    if (!(param.thisObject instanceof Activity)) return;
                    final Activity act = (Activity) param.thisObject;
                    if (!TARGET_PKG.equals(act.getPackageName())) return;
                    boolean hasFocus = (Boolean) param.args[0];
                    if (hasFocus) {
                        sHostActivity = act;
                        if (sGateView == null) {
                            act.runOnUiThread(new Runnable() {
                                @Override public void run() { showGate(act); }
                            });
                        }
                    } else if (sGateOwner == act) {
                        // 失焦时卸载闸门,避免盖到别的窗口上
                        removeGate();
                    }
                }
            });

            // Activity.onDestroy -> 清闸门引用
            XposedHelpers.findAndHookMethod(Activity.class, "onDestroy", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (param.thisObject == sGateOwner) removeGate();
                }
            });

            log("已安装卡密授权闸门 (DecorView 全屏覆盖层, v15)");
        } catch (Throwable e) {
            log("安装卡密闸门失败: " + e);
        }
    }

    // 授权决策:悟空prefs -> 跨进程provider(桌面卡密) -> 文件兜底 -> 闸门UI
    // 关键:provider 读桌面 AuthActivity 写的卡密,清空悟空数据后仍可拿到,
    // 因此用户只需在桌面激活一次,悟空分身里永不再弹闸门输入卡密
    private void showGate(final Activity act) {
        if (sLicensed || sGateView != null) return;

        // 1) 本进程 SharedPreferences(上次镜像/激活写的,清数据才丢)
        if (KeytLicense.isValidFromPrefs(act)) {
            log("本地 prefs 授权有效(card=" + KeytLicense.cardFromPrefs(act)
                    + ") -> 直接装配,不弹闸门");
            enableBypass(act);
            return;
        }

        // 2) 跨进程查桌面 AuthActivity 写入的卡密(LicenseProvider)
        String[] prov = queryProviderCard(act);
        if (prov != null && prov[0] != null && !prov[0].isEmpty()) {
            final String card = prov[0];
            long expire = 0L;
            try { expire = Long.parseLong(prov[1]); } catch (Throwable ignore) {}
            long now = System.currentTimeMillis() / 1000;
            if (expire > 0 && now < expire) {
                // 桌面记录的过期时间未到 -> 信任,镜像到悟空 prefs,离线放行
                KeytLicense.mirror(act, card, expire);
                log("provider 卡密有效(card=" + card + ",expire=" + expire
                        + ") -> 镜像 prefs 并装配,不弹闸门");
                enableBypass(act);
                return;
            }
            // 桌面记录的过期已到 -> 后台联网复验(已关设备绑定,同卡可续)
            // 平台若续期则自动拿到新过期时间;真过期则弹闸门让用户处理
            log("provider 卡密本地已过期,后台联网复验 card=" + card);
            showVerifyingOverlay(act, "正在验证授权…");
            new Thread(new Runnable() {
                @Override public void run() {
                    final String err = KeytLicense.activate(act, card);
                    act.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            removeGate();
                            if (err == null) {
                                enableBypass(act);
                            } else {
                                log("自动复验失败: " + err + " -> 弹闸门兜底");
                                buildAndShowGate(act);
                            }
                        }
                    });
                }
            }, "wklic-auto").start();
            return;
        }

        // 3) 文件兜底(root/旧版路径,通常读不到)
        if (KeytLicense.isValidQuick()) {
            log("文件授权仍然有效 -> 直接装配,不弹闸门");
            enableBypass(act);
            return;
        }

        // 4) 都没有 -> 弹闸门 UI(仅当用户从未在桌面激活过)
        buildAndShowGate(act);
    }

    // 跨进程查 LicenseProvider,返回 [card, expireStr] 或 null
    private String[] queryProviderCard(Activity act) {
        if (act == null) return null;
        android.database.Cursor c = null;
        try {
            android.net.Uri uri = android.net.Uri.parse("content://com.wkvip.lic/license");
            c = act.getContentResolver().query(uri, null, null, null, null);
            if (c == null || !c.moveToFirst()) return null;
            int ci = c.getColumnIndex("card");
            int ei = c.getColumnIndex("expire");
            String card = ci >= 0 ? c.getString(ci) : "";
            String exp = ei >= 0 ? String.valueOf(c.getLong(ei)) : "0";
            if (card == null) card = "";
            log("provider 返回 card=" + card + " expire=" + exp);
            return new String[]{card, exp};
        } catch (Throwable e) {
            log("queryProviderCard 异常: " + e);
            return null;
        } finally {
            if (c != null) try { c.close(); } catch (Throwable ignore) {}
        }
    }

    // 轻量"验证中"遮罩(无输入框),复用 sGateView 生命周期
    private void showVerifyingOverlay(Activity act, String text) {
        try {
            if (act.isFinishing() || act.isDestroyed()) return;
            Window win = act.getWindow();
            if (win == null) return;
            View decor = win.getDecorView();
            if (!(decor instanceof ViewGroup)) return;
            ViewGroup root = (ViewGroup) decor;
            TextView tv = new TextView(act);
            tv.setText(text);
            tv.setTextColor(0xFFFFFFFF);
            tv.setGravity(android.view.Gravity.CENTER);
            tv.setBackgroundColor(0xCC000000);
            tv.setPadding(dp(act, 40), dp(act, 24), dp(act, 40), dp(act, 24));
            root.addView(tv, new ViewGroup.LayoutParams(-1, -1));
            sGateView = tv;
            sGateOwner = act;
        } catch (Throwable e) {
            log("showVerifyingOverlay 异常: " + e);
        }
    }

    // 实际挂载卡密输入闸门(兜底:仅在从未于桌面激活时出现)
    private void buildAndShowGate(final Activity act) {
        if (sLicensed || sGateView != null) return;
        try {
            if (act.isFinishing() || act.isDestroyed()) { retryGate(act); return; }
            Window win = act.getWindow();
            if (win == null) { retryGate(act); return; }
            View decor = win.getDecorView();
            if (decor == null || !(decor instanceof ViewGroup)) {
                log("buildAndShowGate: 无 DecorView, 稍后重试");
                retryGate(act);
                return;
            }
            ViewGroup root = (ViewGroup) decor;
            View gate = buildGateView(act);
            root.addView(gate, new ViewGroup.LayoutParams(-1, -1));
            sGateView = gate;
            sGateOwner = act;
            log("卡密覆盖层已挂载于 " + act.getClass().getSimpleName()
                    + " (decor 子视图数=" + root.getChildCount() + ")");
        } catch (Throwable e) {
            log("挂载卡密覆盖层失败: " + e);
            sGateView = null;
            sGateOwner = null;
            retryGate(act);
        }
    }

    private void removeGate() {
        try {
            View v = sGateView;
            sGateView = null;
            sGateOwner = null;
            if (v != null && v.getParent() instanceof ViewGroup) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        } catch (Throwable e) { log("removeGate err: " + e); }
    }

    private void retryGate(final Activity act) {
        if (sLicensed) return;
        try {
            new Handler(act.getMainLooper()).postDelayed(new Runnable() {
                @Override public void run() { showGate(act); }
            }, 400L);
        } catch (Throwable ignore) {}
    }

    // 激活成功:置授权、移闸门、装 VIP 钩子、起轮询
    private void enableBypass(Activity act) {
        sLicensed = true;
        removeGate();
        if (sBaseLoader != null) {
            tryInstall(sBaseLoader);
            startPolling(sBaseLoader);
        }
        log("授权通过 -> 开始装配 VIP 绕过 hook");
    }

    // ============ 闸门 UI(纯代码构建,无需资源) ============
    private View buildGateView(final Activity act) {
        int dp24 = dp(act, 24);
        int dp20 = dp(act, 20);
        int dp14 = dp(act, 14);
        int dp10 = dp(act, 10);
        int dp26 = dp(act, 26);
        int dp30 = dp(act, 30);

        // 外层:半透明黑遮罩,拦截点击
        LinearLayout root = new LinearLayout(act);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(android.view.Gravity.CENTER);
        root.setBackgroundColor(0xCC000000);
        root.setClickable(true);
        root.setFocusable(true);

        // 卡片容器:白底圆角感(用 padding 模拟)
        LinearLayout card = new LinearLayout(act);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(android.view.Gravity.CENTER);
        card.setBackgroundColor(0xFFFFFFFF);
        card.setPadding(dp24, dp20, dp24, dp24);
        int cardW = Math.min(dp(act, 320), Math.max(dp(act, 260), 0));
        root.addView(card, new LinearLayout.LayoutParams(cardW, -2));

        // 标题
        TextView title = new TextView(act);
        title.setText("卡密激活");
        title.setTextColor(0xFF000000);
        title.setTextSize(20f);
        title.setGravity(android.view.Gravity.CENTER);
        card.addView(title, new LinearLayout.LayoutParams(-1, -2));

        // 提示
        TextView hint = new TextView(act);
        hint.setText("本模块需卡密授权后才能启用 VIP 解锁。\n请输入卡密：");
        hint.setTextColor(0xFF444444);
        hint.setTextSize(14f);
        hint.setPadding(0, dp14, 0, dp10);
        LinearLayout.LayoutParams hintLp = new LinearLayout.LayoutParams(-1, -2);
        card.addView(hint, hintLp);

        // 输入框
        final EditText input = new EditText(act);
        input.setHint("XXXX-XXXX-XXXX-XXXX");
        input.setSingleLine(true);
        input.setInputType(android.text.InputType.TYPE_CLASS_TEXT
                | android.text.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        input.setTextColor(0xFF000000);
        input.setHintTextColor(0xFF888888);
        input.setBackgroundColor(Color.rgb(240, 240, 240));
        card.addView(input, new LinearLayout.LayoutParams(-1, -2));

        // 错误/状态文本
        final TextView err = new TextView(act);
        err.setTextColor(Color.rgb(200, 0, 0));
        err.setTextSize(13f);
        err.setMinHeight(dp26);
        card.addView(err, new LinearLayout.LayoutParams(-1, -2));

        // 按钮行
        LinearLayout btnRow = new LinearLayout(act);
        btnRow.setOrientation(LinearLayout.HORIZONTAL);
        btnRow.setGravity(android.view.Gravity.CENTER);

        // 激活按钮
        TextView btnActivate = new TextView(act);
        btnActivate.setText("激  活");
        btnActivate.setTextColor(0xFFFFFFFF);
        btnActivate.setTextSize(16f);
        btnActivate.setGravity(android.view.Gravity.CENTER);
        btnActivate.setBackgroundColor(Color.rgb(38, 50, 56));
        btnRow.addView(btnActivate, new LinearLayout.LayoutParams(0, -2, 1f));

        // 稍后按钮
        TextView btnLater = new TextView(act);
        btnLater.setText("稍  后");
        btnLater.setTextColor(0xFFFFFFFF);
        btnLater.setTextSize(16f);
        btnLater.setGravity(android.view.Gravity.CENTER);
        btnLater.setBackgroundColor(Color.rgb(140, 140, 140));
        LinearLayout.LayoutParams laterLp = new LinearLayout.LayoutParams(0, -2, 1f);
        laterLp.setMarginStart(dp10);
        btnRow.addView(btnLater, laterLp);

        card.addView(btnRow, new LinearLayout.LayoutParams(-1, -2, 0f));

        // 激活按钮:联网验证 -> 成功 enableBypass
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cardKey = input.getText().toString().trim();
                if (cardKey.isEmpty()) { err.setText("请输入卡密"); return; }
                if (KeytLicense.isDebuggerConnected()) {
                    err.setText("检测到调试环境，无法激活"); return;
                }
                err.setText("正在验证…");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (KeytLicense.isDebuggerConnected()) {
                            act.runOnUiThread(new Runnable() {
                                @Override public void run() {
                                    err.setText("检测到调试环境，无法激活");
                                }
                            });
                            return;
                        }
                        final String e = KeytLicense.activate(act, cardKey);
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (e == null) {
                                    toast(act, "激活成功，VIP 解锁已启用");
                                    enableBypass(act);
                                } else {
                                    err.setText("激活失败：" + e);
                                }
                            }
                        });
                    }
                }, "wklic-activate").start();
            }
        });

        // 稍后按钮:移除闸门,本轮不挡(下个 onResume 再挂)
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeGate();
            }
        });

        input.requestFocus();
        return root;
    }

    private static int dp(Activity act, int v) {
        try {
            return (int) (v * act.getResources().getDisplayMetrics().density + 0.5f);
        } catch (Throwable e) {
            return v;
        }
    }

    private static void toast(final Activity act, final String msg) {
        try {
            act.runOnUiThread(new Runnable() {
                @Override public void run() {
                    Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Throwable ignore) {}
    }

    // ============ 动态 ClassLoader 钩子(授权后才捕类) ============
    private void hookNewClassLoaders() {
        try {
            XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass",
                    String.class, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    // 关键:未授权时不做任何事,避免提前 hook 导致原逻辑被改
                    if (!sLicensed) return;
                    Object res = param.getResult();
                    if (!(res instanceof Class)) return;
                    Class<?> c = (Class<?>) res;
                    String name = c.getName();
                    if (CLS_ENTRY.equals(name)) {
                        installEntryGate(c);
                    } else if (CLS_VIP.equals(name)) {
                        installVipLevel(c);
                    }
                    // n6.n 捕到后即可卸载自己,减少后续 loadClass 开销
                    if (HOOKED.size() >= 1) {
                        try {
                            Method m = XposedHelpers.findMethodExact(
                                    ClassLoader.class, "loadClass", String.class, boolean.class);
                            XposedBridge.unhookMethod(m, this);
                            log("已卸载动态 ClassLoader 覆盖钩子");
                        } catch (Throwable ignore) {}
                    }
                }
            });
            log("已安装动态 ClassLoader 覆盖钩子");
        } catch (Throwable e) {
            log("安装 ClassLoader 钩子失败: " + e);
        }
    }

    // 兜底:授权后直接 findClassIfExists 试装(类已加载但 loadClass 钩子已卸载时)
    private void tryInstall(ClassLoader loader) {
        if (!sLicensed || loader == null) return;
        try {
            Class<?> c = XposedHelpers.findClassIfExists(CLS_ENTRY, loader);
            if (c != null) installEntryGate(c);
        } catch (Throwable e) { log("tryInstall n6.n 失败: " + e); }
        try {
            Class<?> c = XposedHelpers.findClassIfExists(CLS_VIP, loader);
            if (c != null) installVipLevel(c);
        } catch (Throwable e) { log("tryInstall O0.b 失败: " + e); }
    }

    // n6.n.a(String) -> unconditional BYPASS(true)
    private synchronized void installEntryGate(Class<?> clazz) {
        if (HOOKED.contains(clazz)) return;
        try {
            XC_MethodHook.Unhook u = XposedHelpers.findAndHookMethod(clazz, "a", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Object a0 = (param.args != null && param.args.length > 0)
                            ? param.args[0] : "?";
                    log("n6.n.a(\"" + a0 + "\") -> BYPASS(true)");
                    param.setResult(Boolean.TRUE);
                }
            });
            UNHOOKS.add(u);
            HOOKED.add(clazz);
            log("已安装入口 VIP 判定绕过 hook: " + clazz.getName()
                    + " (loader=" + clazz.getClassLoader() + ")");
        } catch (Throwable e) {
            log("安装 n6.n.a hook 失败: " + e);
            // 兜底:枚举所有名为 a 的方法
            try {
                int count = 0;
                for (Method m : clazz.getDeclaredMethods()) {
                    if (!m.getName().equals("a")) continue;
                    XC_MethodHook.Unhook u = XposedBridge.hookMethod(m, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            param.setResult(Boolean.TRUE);
                        }
                    });
                    UNHOOKS.add(u);
                    count++;
                }
                if (count > 0) {
                    HOOKED.add(clazz);
                    log("已安装 n6.n.a x" + count + " (枚举兜底)");
                }
            } catch (Throwable e2) { log("枚举安装 n6.n 失败: " + e2); }
        }
    }

    // O0.b.z() -> 1 (原值为 0 时改写)
    private synchronized void installVipLevel(Class<?> clazz) {
        if (HOOKED.contains(clazz)) return;
        try {
            Method m = XposedHelpers.findMethodExact(clazz, "z");
            XC_MethodHook.Unhook u = XposedBridge.hookMethod(m, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    Object res = param.getResult();
                    if (res instanceof Integer && ((Integer) res) == 0) {
                        param.setResult(1);
                    }
                }
            });
            UNHOOKS.add(u);
            HOOKED.add(clazz);
            log("已安装会员等级兜底 hook: O0.b.z()");
        } catch (Throwable e) {
            log("安装 O0.b.z hook 失败: " + e);
        }
    }

    // 真正撤销所有 VIP hook:遍历 Unhook 句柄卸载,清空状态
    // 装上去的 hook 必须能卸下来,VIP 才会真正失效;只改标志位等于没关
    // 注意:View 操作必须主线程;polling 在后台线程,removeView 要 post 过去
    private synchronized void disableBypass() {
        sLicensed = false;
        sPolling = false;
        synchronized (UNHOOKS) {
            for (XC_MethodHook.Unhook u : UNHOOKS) {
                try { u.unhook(); } catch (Throwable ignore) {}
            }
            UNHOOKS.clear();
        }
        HOOKED.clear();
        // 闸门移除走主线程(后台线程不能操作 View)
        final View v = sGateView;
        sGateView = null;
        sGateOwner = null;
        if (v != null && v.getParent() instanceof ViewGroup) {
            final ViewGroup parent = (ViewGroup) v.getParent();
            Activity act = sHostActivity;
            if (act != null) {
                try {
                    act.runOnUiThread(new Runnable() {
                        @Override public void run() {
                            try { parent.removeView(v); } catch (Throwable ignore) {}
                        }
                    });
                } catch (Throwable ignore) {}
            }
        }
        log("已撤销所有 VIP hook (卡密过期/吊销) -> VIP 立即失效");
    }

    // ============ 后台轮询:复验授权(检测过期/吊销) ============
    // 旧版只查本地过期时间且只改标志位 -> 两个漏洞:
    //   A) hook 没卸,VIP 照样生效(已在 disableBypass 修)
    //   B) 平台吊销检测不到(本地时间没到就不会触发)
    // 现策略:本地过期 -> 联网复验(续卡则更新本地继续 VIP,真过期则卸 hook);
    //        本地有效时每 ~1 小时主动联网复验,检测平台吊销
    private void startPolling(final ClassLoader loader) {
        if (!sLicensed || sPolling) return;
        sPolling = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int tick = 0;
                while (sPolling && sLicensed) {
                    try {
                        Thread.sleep(60000L);
                        if (!sLicensed) break;
                        Activity act = sHostActivity;
                        tick++;

                        // 1) 本地时间检查(快,离线)
                        boolean localValid = false;
                        if (act != null) localValid = KeytLicense.isValidFromPrefs(act);
                        if (!localValid) localValid = KeytLicense.isValidQuick();

                        if (!localValid) {
                            // 本地已过期 -> 联网复验:续卡则续期,真过期则卸 hook
                            log("本地授权已过期,联网复验...");
                            if (!recheckOnline(act, true)) break;
                            continue;
                        }

                        // 2) 每 60 轮(~1 小时)主动联网复验,检测平台吊销
                        //    (避开 keyt.cn 1 分钟频率限制)
                        if (tick % 60 == 0) {
                            log("周期性联网复验(检测吊销)...");
                            if (!recheckOnline(act, false)) break;
                        }
                    } catch (Throwable t) {
                        log("polling 异常: " + t);
                    }
                }
                sPolling = false;
            }
        }, "wkvip-poll").start();
    }

    // 联网复验,返回 true=保持 VIP(成功续期 或 临时网络错误不误杀),false=已卸 hook(真失效)
    private boolean recheckOnline(Activity act, boolean localExpired) {
        if (act == null) {
            // 无 Activity 拿不到卡密 -> 本地过期就卸,否则保留
            if (localExpired) { disableBypass(); return false; }
            return true;
        }
        String card = KeytLicense.cardFromPrefs(act);
        if (card == null || card.isEmpty()) card = KeytLicense.storedCard(act);
        if (card == null || card.isEmpty()) {
            if (localExpired) { disableBypass(); return false; }
            return true;
        }
        String err = KeytLicense.activate(act, card);
        if (err == null) {
            log("联网复验成功(卡密续期/有效),保持 VIP");
            return true;
        }
        // 区分临时网络错误 vs 卡密真失效
        // 临时错误(服务器无响应/请求频繁):不卸 hook,保留 VIP 等待下次重试,避免误杀
        if (err.contains("网络") || err.contains("响应") || err.contains("频繁")) {
            log("联网复验临时错误,保留 VIP 等待重试: " + err);
            return true;
        }
        // 真失效:过期/吊销/无效卡 -> 卸 hook,VIP 立即关闭
        log("卡密失效: " + err + " -> 撤销 hook,VIP 关闭");
        disableBypass();
        return false;
    }
}
