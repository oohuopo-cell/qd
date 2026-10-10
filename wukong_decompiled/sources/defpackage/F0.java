package defpackage;

/* loaded from: assets/cp.dat */
public final class F0 extends AbstractC0053z {
    public int c;

    public F0(boolean z, InterfaceC0016g[] interfaceC0016gArr) {
        super(z, interfaceC0016gArr);
        this.c = -1;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 49);
        D0 b = c0041t.b();
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        int length = interfaceC0016gArr.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            c0041t.h(t());
            while (i < length) {
                b.n(interfaceC0016gArr[i].e());
                i++;
            }
            return;
        }
        AbstractC0045v[] abstractC0045vArr = new AbstractC0045v[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            AbstractC0045v q = interfaceC0016gArr[i3].e().q();
            abstractC0045vArr[i3] = q;
            i2 += q.m(true);
        }
        this.c = i2;
        c0041t.h(i2);
        while (i < length) {
            b.n(abstractC0045vArr[i]);
            i++;
        }
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, t());
    }

    @Override // defpackage.AbstractC0053z, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        return this;
    }

    public final int t() {
        if (this.c < 0) {
            int i = 0;
            for (InterfaceC0016g interfaceC0016g : this.a) {
                i += interfaceC0016g.e().q().m(true);
            }
            this.c = i;
        }
        return this.c;
    }
}
