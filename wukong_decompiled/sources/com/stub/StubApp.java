package com.stub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.TypedValue;
import com.tianyu.util.DtcLoader;
import com.tianyu.util.a;
import dalvik.system.DexFile;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public final class StubApp extends Application {
    private static Context d;
    private static Application a = null;
    public static String strEntryApplication = "entryRunApplication";
    private static Application b = null;
    private static String c = "libjiagu";
    private static boolean loadFromLib = false;
    private static boolean needX86Bridge = true;
    private static boolean returnIntern = true;
    public static boolean isIsolated = false;
    private static String e = null;
    private static String f = null;
    private static String g = null;
    private static String h = null;
    private static String i = null;
    private static Map<Integer, String> j = new ConcurrentHashMap();
    private static Map<String, Set<String>> perm = new ConcurrentHashMap();

    public static void ChangeTopApplication() {
        try {
            interface7(b, a.getBaseContext());
        } catch (Exception e2) {
        }
    }

    private static Application a(Context context) {
        ClassLoader classLoader;
        Class<?> loadClass;
        try {
            if (b == null && (classLoader = context.getClassLoader()) != null && (loadClass = classLoader.loadClass(strEntryApplication)) != null) {
                b = (Application) loadClass.newInstance();
            }
        } catch (Exception e2) {
        }
        return b;
    }

    private static String a(String str, boolean z, boolean z2) {
        String str2 = c;
        if (Build.VERSION.SDK_INT < 23) {
            str2 = str2 + str.hashCode();
        }
        return (!z || z2) ? str2 + ".so" : str2 + "_64.so";
    }

    public static native void fcmark();

    public static Context getAppContext() {
        return d;
    }

    public static String getDir() {
        return h;
    }

    public static Context getOrigApplicationContext(Context context) {
        return context == a ? b : context;
    }

    public static String getSoPath1() {
        return f;
    }

    public static String getSoPath2() {
        return g;
    }

    public static String getString2(int i2) {
        String str = j.get(Integer.valueOf(i2));
        if (str == null) {
            str = interface14(i2);
            j.put(Integer.valueOf(i2), str);
        }
        return (str == null || !returnIntern) ? str : str.intern();
    }

    public static String getString2(String str) {
        try {
            return getString2(Integer.parseInt(str));
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static native void interface11(int i2);

    public static native Enumeration<String> interface12(DexFile dexFile);

    public static native long interface13(int i2, long j2, long j3, long j4, int i3, int i4, long j5);

    public static native String interface14(int i2);

    public static native AssetFileDescriptor interface17(AssetManager assetManager, String str);

    public static native InputStream interface18(Class cls, String str);

    public static native InputStream interface19(ClassLoader classLoader, String str);

    public static native InputStream interface199(AssetManager assetManager, String str);

    public static native void interface20();

    public static native void interface21(Application application);

    public static native void interface22(int i2, String[] strArr, int[] iArr);

    public static native void interface24(Activity activity, String[] strArr, int i2);

    public static native ZipEntry interface30(ZipFile zipFile, String str);

    public static native void interface5(Application application);

    public static native InputStream interface51(Resources resources, int i2);

    public static native InputStream interface52(Resources resources, int i2, TypedValue typedValue);

    public static native AssetFileDescriptor interface53(Resources resources, int i2);

    public static native MediaPlayer interface54(Context context, int i2);

    public static native MediaPlayer interface55(Context context, int i2, AudioAttributes audioAttributes, int i3);

    public static native int interface56(SoundPool soundPool, Context context, int i2, int i3);

    public static native String interface6(String str);

    public static native boolean interface7(Application application, Context context);

    public static native boolean interface8(Application application, Context context);

    public static native void interface99(Application application);

    public static boolean isX86Arch() {
        return a.a();
    }

    public static native Location mark(LocationManager locationManager, String str);

    public static native void mark();

    public static native void mark(Location location);

    public static native synchronized Object n010333(Object obj, Object obj2);

    public static native void n0110();

    public static native int n0111();

    public static native void n01110(int i2);

    public static native boolean n01111(int i2);

    public static native void n011110(int i2, float f2);

    public static native boolean n011111(int i2, boolean z);

    public static native Object n0111113(boolean z, boolean z2, boolean z3);

    public static native void n0111120(boolean z, boolean z2, long j2);

    public static native Object n011113(int i2, int i3);

    public static native long n01112(int i2);

    public static native Object n0111233(int i2, long j2, Object obj);

    public static native long n01112332(int i2, long j2, Object obj, Object obj2);

    public static native Object n01113(boolean z);

    public static native void n011130(boolean z, Object obj);

    public static native boolean n011131(int i2, Object obj);

    public static native boolean n0111311(int i2, Object obj, int i3);

    public static native long n011132(int i2, Object obj);

    public static native long n0111321111112(int i2, Object obj, long j2, boolean z, int i3, int i4, boolean z2, int i5, int i6);

    public static native void n0111330(int i2, Object obj, Object obj2);

    public static native int n0111331(int i2, Object obj, Object obj2);

    public static native int n011133311(int i2, Object obj, Object obj2, Object obj3, int i3);

    public static native long n0112();

    public static native void n01120(long j2);

    public static native boolean n01121(long j2);

    public static native void n011210(long j2, int i2);

    public static native int n011211(long j2, int i2);

    public static native long n011212(long j2, int i2);

    public static native Object n011213(long j2, int i2);

    public static native long n01122(long j2);

    public static native void n011220(long j2, long j3);

    public static native Object n011223(long j2, long j3);

    public static native void n0112230(long j2, long j3, Object obj);

    public static native void n011223220(long j2, long j3, Object obj, long j4, long j5);

    public static native Object n01123(long j2);

    public static native void n011230(long j2, Object obj);

    public static native int n011231(long j2, Object obj);

    public static native void n0112310(long j2, Object obj, int i2);

    public static native int n0112311(long j2, Object obj, int i2);

    public static native void n01123110(long j2, Object obj, int i2, int i3);

    public static native int n01123111(long j2, Object obj, int i2, int i3);

    public static native Object n0112313(long j2, Object obj, int i2);

    public static native void n01123130(long j2, Object obj, boolean z, Object obj2);

    public static native int n01123131331131(long j2, Object obj, int i2, Object obj2, int i3, Object obj3, Object obj4, int i4, int i5, Object obj5);

    public static native long n011232(long j2, Object obj);

    public static native void n0112320(long j2, Object obj, long j3);

    public static native int n011232131(long j2, Object obj, long j3, int i2, Object obj2);

    public static native long n0112322(long j2, Object obj, long j3);

    public static native int n0112322131(long j2, Object obj, long j3, long j4, int i2, Object obj2);

    public static native int n01123231(long j2, Object obj, long j3, Object obj2);

    public static native int n01123231131(long j2, Object obj, long j3, Object obj2, int i2, int i3, Object obj3);

    public static native long n01123232(long j2, Object obj, long j3, Object obj2);

    public static native Object n011233(long j2, Object obj);

    public static native void n0112330(long j2, Object obj, Object obj2);

    public static native int n0112331(long j2, Object obj, Object obj2);

    public static native long n0112332(long j2, Object obj, Object obj2);

    public static native Object n0112333(long j2, Object obj, Object obj2);

    public static native void n01123330(long j2, Object obj, Object obj2, Object obj3);

    public static native void n011233310(long j2, Object obj, Object obj2, Object obj3, int i2);

    public static native Object n011233313(long j2, Object obj, Object obj2, Object obj3, int i2);

    public static native void n011233331110(long j2, Object obj, Object obj2, Object obj3, Object obj4, int i2, int i3, int i4);

    public static native int n011233331111(long j2, Object obj, Object obj2, Object obj3, Object obj4, int i2, int i3, int i4);

    public static native Object n0113();

    public static native void n01130(Object obj);

    public static native boolean n01131(Object obj);

    public static native void n011310(Object obj, int i2);

    public static native int n011311(Object obj, int i2);

    public static native int n0113111(Object obj, int i2, int i3);

    public static native Object n0113113(Object obj, int i2, int i3);

    public static native long n011311312(Object obj, int i2, int i3, Object obj2, boolean z);

    public static native long n011312(Object obj, boolean z);

    public static native int n01131211(Object obj, int i2, long j2, int i3);

    public static native Object n011313(Object obj, int i2);

    public static native void n0113130(Object obj, int i2, Object obj2);

    public static native void n01131310(Object obj, int i2, Object obj2, boolean z);

    public static native void n011313113310(Object obj, int i2, Object obj2, int i3, int i4, Object obj3, Object obj4, int i5);

    public static native void n01131313310(Object obj, int i2, Object obj2, int i3, Object obj3, Object obj4, boolean z);

    public static native Object n0113133(Object obj, int i2, Object obj2);

    public static native void n01131330(Object obj, int i2, Object obj2, Object obj3);

    public static native int n01131331(Object obj, int i2, Object obj2, Object obj3);

    public static native long n011313321111112(Object obj, int i2, Object obj2, Object obj3, long j2, boolean z, int i3, int i4, boolean z2, int i5, int i6);

    public static native Object n01131333(Object obj, int i2, Object obj2, Object obj3);

    public static native void n0113133330(Object obj, int i2, Object obj2, Object obj3, Object obj4, Object obj5);

    public static native long n01132(Object obj);

    public static native int n011321(Object obj, long j2);

    public static native void n0113210(Object obj, long j2, int i2);

    public static native Object n01132113(Object obj, long j2, int i2, boolean z);

    public static native void n01132130(Object obj, long j2, int i2, Object obj2);

    public static native Object n011323(Object obj, long j2);

    public static native long n0113232(Object obj, long j2, Object obj2);

    public static native Object n0113233(Object obj, long j2, Object obj2);

    public static native void n011323310(Object obj, long j2, Object obj2, Object obj3, boolean z);

    public static native Object n01133(Object obj);

    public static native void n011330(Object obj, Object obj2);

    public static native boolean n011331(Object obj, Object obj2);

    public static native void n0113310(Object obj, Object obj2, boolean z);

    public static native boolean n0113311(Object obj, Object obj2, int i2);

    public static native void n01133110(Object obj, Object obj2, int i2, int i3);

    public static native boolean n01133111(Object obj, Object obj2, int i2, int i3);

    public static native int n0113311111(Object obj, Object obj2, boolean z, boolean z2, int i2, boolean z3);

    public static native void n0113311120(Object obj, Object obj2, int i2, boolean z, boolean z2, long j2);

    public static native Object n0113313(Object obj, Object obj2, int i2);

    public static native void n01133130(Object obj, Object obj2, int i2, Object obj3);

    public static native int n0113313111(Object obj, Object obj2, int i2, Object obj3, int i3, int i4);

    public static native Object n011331313(Object obj, Object obj2, int i2, Object obj3, int i3);

    public static native long n011332(Object obj, Object obj2);

    public static native void n0113320(Object obj, Object obj2, long j2);

    public static native boolean n0113321(Object obj, Object obj2, long j2);

    public static native Object n0113323233(Object obj, Object obj2, long j2, Object obj3, long j3, Object obj4);

    public static native Object n011333(Object obj, Object obj2);

    public static native void n0113330(Object obj, Object obj2, Object obj3);

    public static native boolean n0113331(Object obj, Object obj2, Object obj3);

    public static native void n01133310(Object obj, Object obj2, Object obj3, int i2);

    public static native int n01133311(Object obj, Object obj2, Object obj3, int i2);

    public static native long n01133312(Object obj, Object obj2, Object obj3, int i2);

    public static native void n011333130(Object obj, Object obj2, Object obj3, int i2, Object obj4);

    public static native long n0113332(Object obj, Object obj2, Object obj3);

    public static native Object n0113333(Object obj, Object obj2, Object obj3);

    public static native void n01133330(Object obj, Object obj2, Object obj3, Object obj4);

    public static native int n0113333111(Object obj, Object obj2, Object obj3, Object obj4, int i2, int i3);

    public static native Object n011333321133(Object obj, Object obj2, Object obj3, Object obj4, long j2, boolean z, boolean z2, Object obj5);

    public static native Object n011333323(Object obj, Object obj2, Object obj3, Object obj4, long j2);

    public static native void n0113333331113310(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, int i2, int i3, int i4, Object obj7, Object obj8, int i5);

    public static native long n01133333312(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, int i2);

    public static native void n011333333130(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, int i2, Object obj7);

    public static native long n011333333332(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8);

    public static native void n0113333333333330(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12);

    public static native void pmark(Context context);

    public static void put(Object obj, String[] strArr) {
        try {
            String canonicalName = obj.getClass().getCanonicalName();
            Set<String> set = perm.get(canonicalName);
            if (set != null) {
                set.addAll(Arrays.asList(strArr));
            } else {
                Set<String> newSetFromMap = Collections.newSetFromMap(new ConcurrentHashMap());
                Collections.addAll(newSetFromMap, strArr);
                Set<String> put = perm.put(canonicalName, newSetFromMap);
                if (put != null) {
                    put.addAll(Arrays.asList(strArr));
                }
            }
        } catch (Exception e2) {
        }
    }

    public static native void rmark();

    @Override // android.content.ContextWrapper
    protected final void attachBaseContext(Context context) {
        System.currentTimeMillis();
        super.attachBaseContext(context);
        a.b();
        d = context;
        if (a == null) {
            a = this;
        }
        if (b == null) {
            Boolean valueOf = Boolean.valueOf(a.a());
            Boolean bool = Build.CPU_ABI.contains("64") || Build.CPU_ABI2.contains("64");
            Boolean bool2 = Build.CPU_ABI.contains("mips") || Build.CPU_ABI2.contains("mips");
            if (valueOf.booleanValue() && needX86Bridge) {
                System.loadLibrary("X86Bridge");
            }
            if (!loadFromLib) {
                String absolutePath = context.getFilesDir().getParentFile().getAbsolutePath();
                try {
                    absolutePath = context.getFilesDir().getParentFile().getCanonicalPath();
                } catch (Exception e2) {
                }
                String str = absolutePath + "/.jiagu";
                i = a(str, bool.booleanValue(), bool2.booleanValue());
                e = a(str, false, false);
                f = str + File.separator + e;
                g = str + File.separator + i;
                h = str;
                if (bool2.booleanValue()) {
                    a.a(context, c + "_mips.so", str, e);
                } else if (!valueOf.booleanValue() || needX86Bridge) {
                    a.a(context, c + ".so", str, e);
                } else {
                    a.a(context, c + "_x86.so", str, e);
                }
                if (!bool.booleanValue() || bool2.booleanValue()) {
                    System.load(str + "/" + e);
                } else {
                    if ((!valueOf.booleanValue() || needX86Bridge) ? a.a(context, c + "_a64.so", str, i) : a.a(context, c + "_x64.so", str, i)) {
                        System.load(str + "/" + i);
                    } else {
                        System.load(str + "/" + e);
                    }
                }
            } else if (!valueOf.booleanValue() || needX86Bridge) {
                System.loadLibrary("jiagu");
            } else {
                System.loadLibrary("jiagu_x86");
            }
        }
        DtcLoader.init();
        if (isIsolated) {
            return;
        }
        interface5(a);
        if (b == null) {
            b = a(context);
            if (b == null) {
                System.exit(1);
                return;
            }
            try {
                Method declaredMethod = Application.class.getDeclaredMethod("attach", Context.class);
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(b, context);
                }
                interface99(b);
                interface8(b, context);
            } catch (Exception e3) {
                throw new RuntimeException("Failed to call attachBaseContext.", e3);
            }
        }
    }

    public native synchronized boolean n110331(Object obj, Object obj2);

    public native void n1110();

    public native boolean n1111();

    public native void n11110(int i2);

    public native int n11111(int i2);

    public native void n1111130(int i2, int i3, Object obj);

    public native void n111113310(int i2, int i3, Object obj, Object obj2, boolean z);

    public native Object n11113(int i2);

    public native int n1111331(int i2, Object obj, Object obj2);

    public native void n111133310(boolean z, Object obj, Object obj2, Object obj3, boolean z2);

    public native double n1112();

    public native void n11120(double d2);

    public native long n111212(long j2, boolean z);

    public native Object n111213(long j2, boolean z);

    public native long n11122(long j2);

    public native long n111222(long j2, long j3);

    public native void n111230(long j2, Object obj);

    public native boolean n111231(long j2, Object obj);

    public native boolean n1112311(long j2, Object obj, boolean z);

    public native boolean n11123111(long j2, Object obj, boolean z, int i2);

    public native boolean n1112321(long j2, Object obj, double d2);

    public native boolean n11123211(long j2, Object obj, double d2, int i2);

    public native double n1112322(long j2, Object obj, double d2);

    public native Object n111233(long j2, Object obj);

    public native boolean n1112331(long j2, Object obj, Object obj2);

    public native boolean n11123311(long j2, Object obj, Object obj2, int i2);

    public native Object n1112333(long j2, Object obj, Object obj2);

    public native Object n1113();

    public native void n11130(Object obj);

    public native int n11131(Object obj);

    public native void n111310(Object obj, int i2);

    public native boolean n111311(Object obj, int i2);

    public native int n1113111(Object obj, int i2, int i3);

    public native boolean n11131131(Object obj, int i2, int i3, Object obj2);

    public native Object n111313(Object obj, int i2);

    public native void n1113130(Object obj, boolean z, Object obj2);

    public native boolean n111313131(Object obj, int i2, Object obj2, int i3, Object obj3);

    public native boolean n11131331(Object obj, int i2, Object obj2, Object obj3);

    public native Object n11133(Object obj);

    public native void n111330(Object obj, Object obj2);

    public native int n111331(Object obj, Object obj2);

    public native void n1113310(Object obj, Object obj2, boolean z);

    public native void n11133110(Object obj, Object obj2, int i2, boolean z);

    public native int n11133111(Object obj, Object obj2, int i2, int i3);

    public native Object n111333(Object obj, Object obj2);

    public native void n1113330(Object obj, Object obj2, Object obj3);

    public native boolean n1113331(Object obj, Object obj2, Object obj3);

    public native void n11133310(Object obj, Object obj2, Object obj3, int i2);

    public native Object n1113333(Object obj, Object obj2, Object obj3);

    public native int n11133331(Object obj, Object obj2, Object obj3, Object obj4);

    public native void n1113333130(Object obj, Object obj2, Object obj3, Object obj4, int i2, Object obj5);

    public native Object n111333333(Object obj, Object obj2, Object obj3, Object obj4, Object obj5);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.reflect.Method] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    @Override // android.app.Application
    public final void onCreate() {
        System.currentTimeMillis();
        super.onCreate();
        if (isIsolated) {
            return;
        }
        ChangeTopApplication();
        if (b != null) {
            b.onCreate();
        }
        interface21(b);
        ?? r5 = this;
        if (b != null) {
            r5 = b;
        }
        Context context = d;
        if (r5 == 0 || context == null || !a.a(context)) {
            return;
        }
        try {
            ?? declaredMethod = Class.forName(a.a("s\u007f}>zw>rx>Bu`\u007fbdcDy}u")).getDeclaredMethod(a.a("BuwycdubQsdyfydiSq||Rqs{c"), Application.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, new Object[]{r5});
        } catch (Exception e2) {
        }
    }
}
