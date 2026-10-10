package defpackage;

/* loaded from: assets/cp.dat */
public final class W extends AbstractC0053z {
    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.k(z, 49, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        int i = z ? 4 : 3;
        for (InterfaceC0016g interfaceC0016g : this.a) {
            i += interfaceC0016g.e().m(true);
        }
        return i;
    }
}
