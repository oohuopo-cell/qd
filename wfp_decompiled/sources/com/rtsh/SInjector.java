package com.rtsh;

import android.content.Context;
import android.util.Log;

/* loaded from: /workspace/wfp_patch/classes.dex */
public class SInjector {
    public static String TAG = "SInjector";

    public static void entry() {
        new Exception("Call_Stack").printStackTrace();
    }

    public static void entry(int i) {
        Log.d(TAG, "value(int):" + i);
    }

    public static void entry(Context context) {
        Log.d(TAG, "entry.");
        if (context != null) {
            Log.d(TAG, "ctx info:" + context.getClass().toString() + ";  ctx.classLoader:" + context.getClassLoader().toString() + ";  appInfo:" + context.getApplicationInfo().toString());
        } else {
            Log.d(TAG, "Ren‘body is null.");
        }
        Log.d(TAG, "end");
    }

    public static void entry(Object obj) {
        if (obj == null) {
            Log.d(TAG, "obj: null");
            return;
        }
        Log.d(TAG, "obj:" + obj.toString());
    }
}
