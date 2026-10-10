package defpackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* renamed from: k, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public class C0024k extends AbstractC0045v {
    public final byte[] a;

    public C0024k(String str) {
        this.a = u1.b(str);
        try {
            t();
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public C0024k(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("GeneralizedTime string too short");
        }
        this.a = bArr;
        if (!y(0) || !y(1) || !y(2) || !y(3)) {
            throw new IllegalArgumentException("illegal characters in GeneralizedTime string");
        }
    }

    public static String s(int i) {
        if (i >= 10) {
            return Integer.toString(i);
        }
        return "0" + i;
    }

    public static String z(String str) {
        String str2;
        StringBuilder sb;
        char charAt;
        String substring = str.substring(14);
        int i = 1;
        while (i < substring.length() && '0' <= (charAt = substring.charAt(i)) && charAt <= '9') {
            i++;
        }
        int i2 = i - 1;
        if (i2 > 3) {
            str2 = substring.substring(0, 4) + substring.substring(i);
            sb = new StringBuilder();
        } else if (i2 == 1) {
            str2 = substring.substring(0, i) + "00" + substring.substring(i);
            sb = new StringBuilder();
        } else {
            if (i2 != 2) {
                return str;
            }
            str2 = substring.substring(0, i) + "0" + substring.substring(i);
            sb = new StringBuilder();
        }
        sb.append(str.substring(0, 14));
        sb.append(str2);
        return sb.toString();
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0024k)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0024k) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 24, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        return new C0024k(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        return new C0024k(this.a);
    }

    public final SimpleDateFormat r() {
        SimpleDateFormat simpleDateFormat = v() ? new SimpleDateFormat("yyyyMMddHHmmss.SSSz") : x() ? new SimpleDateFormat("yyyyMMddHHmmssz") : w() ? new SimpleDateFormat("yyyyMMddHHmmz") : new SimpleDateFormat("yyyyMMddHHz");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        return simpleDateFormat;
    }

    public final Date t() {
        SimpleDateFormat r;
        String a = u1.a(this.a);
        if (a.endsWith("Z")) {
            r = v() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'") : x() ? new SimpleDateFormat("yyyyMMddHHmmss'Z'") : w() ? new SimpleDateFormat("yyyyMMddHHmm'Z'") : new SimpleDateFormat("yyyyMMddHH'Z'");
            r.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if (a.indexOf(45) > 0 || a.indexOf(43) > 0) {
            a = u();
            r = r();
        } else {
            r = v() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS") : x() ? new SimpleDateFormat("yyyyMMddHHmmss") : w() ? new SimpleDateFormat("yyyyMMddHHmm") : new SimpleDateFormat("yyyyMMddHH");
            r.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }
        if (v()) {
            a = z(a);
        }
        return H0.a(r.parse(a));
    }

    public final String u() {
        String str;
        String a = u1.a(this.a);
        if (a.charAt(a.length() - 1) == 'Z') {
            return a.substring(0, a.length() - 1) + "GMT+00:00";
        }
        int length = a.length();
        char charAt = a.charAt(length - 6);
        if ((charAt == '-' || charAt == '+') && a.indexOf("GMT") == length - 9) {
            return a;
        }
        int length2 = a.length();
        int i = length2 - 5;
        char charAt2 = a.charAt(i);
        if (charAt2 == '-' || charAt2 == '+') {
            StringBuilder sb = new StringBuilder();
            sb.append(a.substring(0, i));
            sb.append("GMT");
            int i2 = length2 - 2;
            sb.append(a.substring(i, i2));
            sb.append(":");
            sb.append(a.substring(i2));
            return sb.toString();
        }
        int length3 = a.length() - 3;
        char charAt3 = a.charAt(length3);
        if (charAt3 == '-' || charAt3 == '+') {
            return a.substring(0, length3) + "GMT" + a.substring(length3) + ":00";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a);
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset < 0) {
            rawOffset = -rawOffset;
            str = "-";
        } else {
            str = "+";
        }
        int i3 = rawOffset / 3600000;
        int i4 = (rawOffset - (3600000 * i3)) / 60000;
        try {
            if (timeZone.useDaylightTime()) {
                if (v()) {
                    a = z(a);
                }
                if (timeZone.inDaylightTime(r().parse(a + "GMT" + str + s(i3) + ":" + s(i4)))) {
                    i3 += str.equals("+") ? 1 : -1;
                }
            }
        } catch (ParseException unused) {
        }
        sb2.append("GMT" + str + s(i3) + ":" + s(i4));
        return sb2.toString();
    }

    public final boolean v() {
        int i = 0;
        while (true) {
            byte[] bArr = this.a;
            if (i == bArr.length) {
                return false;
            }
            if (bArr[i] == 46 && i == 14) {
                return true;
            }
            i++;
        }
    }

    public final boolean w() {
        return y(10) && y(11);
    }

    public final boolean x() {
        return y(12) && y(13);
    }

    public final boolean y(int i) {
        byte b;
        byte[] bArr = this.a;
        return bArr.length > i && (b = bArr[i]) >= 48 && b <= 57;
    }
}
