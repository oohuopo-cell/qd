package defpackage;

/* renamed from: r0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0038r0 extends AbstractC0051y {
    public int c = -1;

    public final int A() {
        if (this.c < 0) {
            int length = this.a.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.a[i2].e().p().m(true);
            }
            this.c = i;
        }
        return this.c;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 48);
        C0035p0 a = c0041t.a();
        int length = this.a.length;
        int i = 0;
        if (this.c >= 0 || length > 16) {
            c0041t.h(A());
            while (i < length) {
                this.a[i].e().p().j(a, true);
                i++;
            }
            return;
        }
        AbstractC0045v[] abstractC0045vArr = new AbstractC0045v[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            AbstractC0045v p = this.a[i3].e().p();
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
        return C0041t.d(z, A());
    }

    @Override // defpackage.AbstractC0051y, defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        return this;
    }

    @Override // defpackage.AbstractC0051y, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        return this;
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0006c x() {
        return new AbstractC0006c(O.t(r()), false);
    }

    @Override // defpackage.AbstractC0051y
    public final r y() {
        return new r(S.s(s()));
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0053z z() {
        return new F0(false, this.a);
    }
}
