package com.ep;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.util.Log;
import dalvik.system.DexClassLoader;
import java.lang.reflect.InvocationTargetException;

/* loaded from: /workspace/wfp_patch/classes.dex */
public class DP {
    public static String TAG = "DP";
    private static volatile boolean isOneTime = false;

    public static void entry(Object obj) {
        Log.i(TAG, "DP start.");
        if (isOneTime) {
            Log.i(TAG, "Call one time.");
            return;
        }
        isOneTime = true;
        Context context = (Context) obj;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageManager.getNameForUid(Process.myUid()), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            Log.e(TAG, "packageInfo is null");
            return;
        }
        Log.i(TAG, "packageInfo:" + (packageInfo != null ? packageInfo.toString() : "null"));
        String str = packageInfo.applicationInfo.sourceDir;
        String str2 = "/data/data/" + packageInfo.packageName + "/code_cache";
        Log.i(TAG, "fullPatchPath: " + str + ";  optimizedDir:" + str2);
        try {
            new DexClassLoader(str, str2, packageInfo.applicationInfo.nativeLibraryDir, DP.class.getClassLoader()).loadClass("com.ap.wp.WB").getMethod("cp", Context.class).invoke(null, context);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        Log.d(TAG, "DP exit.");
    }
}
