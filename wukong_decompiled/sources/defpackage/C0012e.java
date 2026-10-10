package defpackage;

/* renamed from: e, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0012e extends AbstractC0045v {
    public static final C0012e b;
    public static final C0012e c;
    public final byte a;

    static {
        new C0003b(C0012e.class, 1);
        b = new C0012e((byte) 0);
        c = new C0012e((byte) -1);
    }

    public C0012e(byte b2) {
        this.a = b2;
    }

    public static C0012e r(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        }
        byte b2 = bArr[0];
        return b2 != -1 ? b2 != 0 ? new C0012e(b2) : b : c;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return s() ? 1 : 0;
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        return (abstractC0045v instanceof C0012e) && s() == ((C0012e) abstractC0045v).s();
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 1);
        c0041t.h(1);
        c0041t.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, 1);
    }

    @Override // defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        return s() ? c : b;
    }

    public final boolean s() {
        return this.a != 0;
    }

    public final String toString() {
        return s() ? "TRUE" : "FALSE";
    }
}
