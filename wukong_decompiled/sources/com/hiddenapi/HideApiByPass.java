package com.hiddenapi;

import java.lang.reflect.Method;

/* loaded from: assets/dpsdk/hidden_api_bypass.dex */
public class HideApiByPass {
    public static void bypass() throws Exception {
        Method declaredMethod = Class.class.getDeclaredMethod("forName", String.class);
        Method declaredMethod2 = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
        Class cls = (Class) declaredMethod.invoke(null, "dalvik.system.VMRuntime");
        ((Method) declaredMethod2.invoke(cls, "setHiddenApiExemptions", new Class[]{String[].class})).invoke(((Method) declaredMethod2.invoke(cls, "getRuntime", null)).invoke(null, new Object[0]), new String[]{"L"});
    }
}
