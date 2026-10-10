package defpackage;

import java.util.Arrays;

/* renamed from: n0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0031n0 extends AbstractC0045v implements B {
    public final byte[] a;

    public C0031n0(byte[] bArr) {
        this.a = bArr;
    }

    @Override // defpackage.B
    public final String a() {
        return u1.a(this.a);
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0031n0)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0031n0) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 18, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    public final String toString() {
        return u1.a(this.a);
    }
}
