package defpackage;

/* renamed from: a, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0000a extends AbstractC0045v implements W0 {
    public final X a;

    public AbstractC0000a(X x) {
        if (64 != x.b) {
            throw new IllegalArgumentException();
        }
        this.a = x;
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return this;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        X x;
        if (abstractC0045v instanceof AbstractC0000a) {
            x = ((AbstractC0000a) abstractC0045v).a;
        } else {
            if (!(abstractC0045v instanceof X)) {
                return false;
            }
            x = (X) abstractC0045v;
        }
        return this.a.n(x);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        this.a.j(c0041t, z);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return this.a.k();
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return this.a.m(z);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        return new M((X) this.a.p(), 1);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        return new AbstractC0000a((X) this.a.q());
    }
}
