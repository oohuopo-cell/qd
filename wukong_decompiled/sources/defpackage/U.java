package defpackage;

/* loaded from: assets/cp.dat */
public final class U extends AbstractC0051y {
    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.k(z, 48, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        int i = z ? 4 : 3;
        int length = this.a.length;
        for (int i2 = 0; i2 < length; i2++) {
            i += this.a[i2].e().m(true);
        }
        return i;
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0006c x() {
        return new O(r());
    }

    @Override // defpackage.AbstractC0051y
    public final r y() {
        r[] s = s();
        return new S(S.s(s), s);
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0053z z() {
        return new AbstractC0053z(false, this.a);
    }
}
