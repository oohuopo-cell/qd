package defpackage;

import java.math.BigInteger;
import java.util.Arrays;

/* renamed from: i, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0020i extends AbstractC0045v {
    public static final C0020i[] b = new C0020i[12];
    public final byte[] a;

    public C0020i(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.a = BigInteger.valueOf(i).toByteArray();
    }

    public C0020i(byte[] bArr, boolean z) {
        if (C0028m.u(bArr)) {
            throw new IllegalArgumentException("malformed enumerated");
        }
        int i = 0;
        if ((bArr[0] & 128) != 0) {
            throw new IllegalArgumentException("enumerated must be non-negative");
        }
        this.a = z ? D.d(bArr) : bArr;
        int length = bArr.length - 1;
        while (i < length) {
            byte b2 = bArr[i];
            i++;
            if (b2 != (bArr[i] >> 7)) {
                return;
            }
        }
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0020i)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0020i) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 10, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }
}
