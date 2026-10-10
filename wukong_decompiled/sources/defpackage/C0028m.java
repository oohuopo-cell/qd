package defpackage;

import java.math.BigInteger;
import java.util.Arrays;

/* renamed from: m, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0028m extends AbstractC0045v {
    public static final C0003b c = new C0003b(C0028m.class, 3);
    public final byte[] a;
    public final int b;

    public C0028m(long j) {
        this.a = BigInteger.valueOf(j).toByteArray();
        this.b = 0;
    }

    public C0028m(BigInteger bigInteger) {
        this.a = bigInteger.toByteArray();
        this.b = 0;
    }

    public C0028m(byte[] bArr) {
        if (u(bArr)) {
            throw new IllegalArgumentException("malformed integer");
        }
        this.a = bArr;
        int length = bArr.length - 1;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (bArr[i] != (bArr[i2] >> 7)) {
                break;
            } else {
                i = i2;
            }
        }
        this.b = i;
    }

    public static C0028m r(Object obj) {
        if (obj == null || (obj instanceof C0028m)) {
            return (C0028m) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
        }
        try {
            C0003b c0003b = c;
            AbstractC0045v o = AbstractC0045v.o((byte[]) obj);
            c0003b.a(o);
            return (C0028m) o;
        } catch (Exception e) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
        }
    }

    public static boolean u(byte[] bArr) {
        int length = bArr.length;
        if (length != 0) {
            return (length == 1 || bArr[0] != (bArr[1] >> 7) || n1.b("org.bouncycastle.asn1.allow_unsafe_integer")) ? false : true;
        }
        return true;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0028m)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0028m) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 2, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    public final BigInteger s() {
        return new BigInteger(this.a);
    }

    public final boolean t(int i) {
        byte[] bArr = this.a;
        int length = bArr.length;
        int i2 = this.b;
        if (length - i2 > 4) {
            return false;
        }
        int length2 = bArr.length;
        int max = Math.max(i2, length2 - 4);
        int i3 = bArr[max];
        while (true) {
            max++;
            if (max >= length2) {
                break;
            }
            i3 = (i3 << 8) | (bArr[max] & 255);
        }
        return i3 == i;
    }

    public final String toString() {
        return s().toString();
    }
}
