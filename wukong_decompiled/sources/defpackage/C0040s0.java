package defpackage;

/* renamed from: s0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0040s0 extends AbstractC0053z {
    public int c;

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 49);
        C0035p0 a = c0041t.a();
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        int length = interfaceC0016gArr.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            c0041t.h(t());
            while (i < length) {
                interfaceC0016gArr[i].e().p().j(a, true);
                i++;
            }
            return;
        }
        AbstractC0045v[] abstractC0045vArr = new AbstractC0045v[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            AbstractC0045v p = interfaceC0016gArr[i3].e().p();
            abstractC0045vArr[i3] = p;
            i2 += p.m(true);
        }
        this.c = i2;
        c0041t.h(i2);
        while (i < length) {
            abstractC0045vArr[i].j(a, true);
            i++;
        }
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, t());
    }

    @Override // defpackage.AbstractC0053z, defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        return this.b ? this : super.p();
    }

    @Override // defpackage.AbstractC0053z, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        return this;
    }

    public final int t() {
        if (this.c < 0) {
            int i = 0;
            for (InterfaceC0016g interfaceC0016g : this.a) {
                i += interfaceC0016g.e().p().m(true);
            }
            this.c = i;
        }
        return this.c;
    }
}
