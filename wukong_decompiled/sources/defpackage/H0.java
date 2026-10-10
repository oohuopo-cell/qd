package defpackage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: assets/cp.dat */
public abstract class H0 {
    public static final HashMap a = new HashMap();

    static {
        if ("en".equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            Locale.getDefault();
            return;
        }
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (int i = 0; i != availableLocales.length; i++) {
            if ("en".equalsIgnoreCase(availableLocales[i].getLanguage())) {
                Locale locale = availableLocales[i];
                return;
            }
        }
        Locale.getDefault();
    }

    public static Date a(Date date) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return date;
        }
        HashMap hashMap = a;
        synchronized (hashMap) {
            try {
                Long l = (Long) hashMap.get(locale);
                if (l == null) {
                    long time = new SimpleDateFormat("yyyyMMddHHmmssz").parse("19700101000000GMT+00:00").getTime();
                    l = time == 0 ? 0L : Long.valueOf(time);
                    hashMap.put(locale, l);
                }
                if (l != 0L) {
                    return new Date(date.getTime() - l.longValue());
                }
                return date;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
