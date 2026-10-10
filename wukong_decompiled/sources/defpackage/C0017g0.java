package defpackage;

/* renamed from: g0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0017g0 extends AbstractC0006c {
    public static C0017g0 t(Object obj) {
        if (obj == null || (obj instanceof C0017g0)) {
            return (C0017g0) obj;
        }
        if (obj instanceof AbstractC0006c) {
            return (C0017g0) ((AbstractC0006c) obj).p();
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
        }
        try {
            return (C0017g0) ((AbstractC0006c) AbstractC0045v.o((byte[]) obj)).p();
        } catch (Exception e) {
            throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
        }
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        byte[] bArr = this.a;
        int i = bArr[0] & 255;
        int length = bArr.length;
        int i2 = length - 1;
        byte b = bArr[i2];
        byte b2 = (byte) ((255 << i) & b);
        if (b == b2) {
            c0041t.j(z, 3, bArr);
            return;
        }
        c0041t.m(z, 3);
        c0041t.h(length);
        c0041t.g(bArr, 0, i2);
        c0041t.f(b2);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    @Override // defpackage.AbstractC0006c, defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        return this;
    }

    @Override // defpackage.AbstractC0006c, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        return this;
    }
}
