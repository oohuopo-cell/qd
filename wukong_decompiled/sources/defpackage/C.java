package defpackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/* loaded from: assets/cp.dat */
public class C extends AbstractC0045v {
    public final byte[] a;

    public C(String str) {
        this.a = u1.b(str);
        try {
            H0.a(new SimpleDateFormat("yyMMddHHmmssz").parse(r()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public C(byte[] bArr) {
        byte b;
        byte b2;
        if (bArr.length < 2) {
            throw new IllegalArgumentException("UTCTime string too short");
        }
        this.a = bArr;
        if (bArr.length <= 0 || (b = bArr[0]) < 48 || b > 57 || bArr.length <= 1 || (b2 = bArr[1]) < 48 || b2 > 57) {
            throw new IllegalArgumentException("illegal characters in UTCTime string");
        }
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C)) {
            return false;
        }
        return Arrays.equals(this.a, ((C) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 23, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    public final String r() {
        StringBuilder sb;
        String substring;
        String a = u1.a(this.a);
        if (a.indexOf(45) >= 0 || a.indexOf(43) >= 0) {
            int indexOf = a.indexOf(45);
            if (indexOf < 0) {
                indexOf = a.indexOf(43);
            }
            if (indexOf == a.length() - 3) {
                a = a.concat("00");
            }
            if (indexOf == 10) {
                sb = new StringBuilder();
                sb.append(a.substring(0, 10));
                sb.append("00GMT");
                sb.append(a.substring(10, 13));
                sb.append(":");
                substring = a.substring(13, 15);
            } else {
                sb = new StringBuilder();
                sb.append(a.substring(0, 12));
                sb.append("GMT");
                sb.append(a.substring(12, 15));
                sb.append(":");
                substring = a.substring(15, 17);
            }
        } else if (a.length() == 11) {
            sb = new StringBuilder();
            sb.append(a.substring(0, 10));
            substring = "00GMT+00:00";
        } else {
            sb = new StringBuilder();
            sb.append(a.substring(0, 12));
            substring = "GMT+00:00";
        }
        sb.append(substring);
        return sb.toString();
    }

    public final String toString() {
        return u1.a(this.a);
    }
}
