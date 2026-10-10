package defpackage;

/* renamed from: o, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0032o extends AbstractC0045v {
    public final C0025k0 a;

    public C0032o(C0025k0 c0025k0) {
        this.a = c0025k0;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return ~D.f(this.a.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (abstractC0045v instanceof C0032o) {
            return this.a.i(((C0032o) abstractC0045v).a);
        }
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 7);
        c0041t.j(false, 25, this.a.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return this.a.m(z);
    }

    @Override // defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        this.a.getClass();
        return this;
    }

    @Override // defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        this.a.getClass();
        return this;
    }
}
