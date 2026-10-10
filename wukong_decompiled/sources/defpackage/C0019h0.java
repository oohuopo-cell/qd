package defpackage;

/* renamed from: h0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0019h0 extends AbstractC0045v {
    public C0036q a;
    public C0028m b;
    public AbstractC0045v c;
    public int d;
    public AbstractC0045v e;
    public final /* synthetic */ int f = 0;

    public /* synthetic */ C0019h0() {
    }

    public C0019h0(AbstractC0051y abstractC0051y) {
        int i;
        AbstractC0045v e;
        AbstractC0045v s = s(abstractC0051y, 0);
        if (s instanceof C0036q) {
            this.a = (C0036q) s;
            s = s(abstractC0051y, 1);
            i = 1;
        } else {
            i = 0;
        }
        if (s instanceof C0028m) {
            this.b = (C0028m) s;
            i++;
            s = s(abstractC0051y, i);
        }
        if (!(s instanceof X)) {
            this.c = s;
            i++;
            s = s(abstractC0051y, i);
        }
        if (abstractC0051y.w() != i + 1) {
            throw new IllegalArgumentException("input sequence too large");
        }
        if (!(s instanceof X)) {
            throw new IllegalArgumentException("No tagged object found in sequence. Structure doesn't seem to be of type External");
        }
        X x = (X) s;
        int i2 = x.c;
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException("invalid encoding value: " + i2);
        }
        this.d = i2;
        int i3 = x.b;
        if (128 != i3) {
            throw new IllegalArgumentException("invalid tag: " + D.e(i3, i2));
        }
        if (i2 != 0) {
            if (i2 == 1) {
                e = (r) r.b.c(x, false);
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("invalid tag: " + D.e(i3, i2));
                }
                e = (AbstractC0006c) AbstractC0006c.b.c(x, false);
            }
        } else {
            if (!x.t()) {
                throw new IllegalStateException("object implicit - explicit expected.");
            }
            InterfaceC0016g interfaceC0016g = x.d;
            e = (interfaceC0016g instanceof AbstractC0030n ? (AbstractC0030n) interfaceC0016g : interfaceC0016g.e()).e();
        }
        this.e = e;
    }

    public static AbstractC0045v s(AbstractC0051y abstractC0051y, int i) {
        if (abstractC0051y.w() > i) {
            return abstractC0051y.u(i).e();
        }
        throw new IllegalArgumentException("too few objects in input sequence");
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        C0036q c0036q = this.a;
        int hashCode = c0036q == null ? 0 : c0036q.hashCode();
        C0028m c0028m = this.b;
        int hashCode2 = hashCode ^ (c0028m == null ? 0 : c0028m.hashCode());
        AbstractC0045v abstractC0045v = this.c;
        return ((hashCode2 ^ (abstractC0045v != null ? abstractC0045v.hashCode() : 0)) ^ this.d) ^ this.e.hashCode();
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        C0019h0 c0019h0;
        Object obj;
        C0036q c0036q;
        if (this == abstractC0045v) {
            return true;
        }
        if (!(abstractC0045v instanceof C0019h0) || ((c0036q = this.a) != (obj = (c0019h0 = (C0019h0) abstractC0045v).a) && (c0036q == null || obj == null || !c0036q.equals(obj)))) {
            return false;
        }
        C0028m c0028m = this.b;
        C0028m c0028m2 = c0019h0.b;
        if (c0028m != c0028m2 && (c0028m == null || c0028m2 == null || !c0028m.equals(c0028m2))) {
            return false;
        }
        AbstractC0045v abstractC0045v2 = this.c;
        AbstractC0045v abstractC0045v3 = c0019h0.c;
        return (abstractC0045v2 == abstractC0045v3 || !(abstractC0045v2 == null || abstractC0045v3 == null || !abstractC0045v2.equals(abstractC0045v3))) && this.d == c0019h0.d && this.e.n(c0019h0.e);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.m(z, 40);
        r().j(c0041t, false);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return true;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return r().m(z);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        switch (this.f) {
            case 0:
                return this;
            default:
                C0019h0 c0019h0 = new C0019h0();
                c0019h0.a = this.a;
                c0019h0.b = this.b;
                c0019h0.c = this.c;
                int i = this.d;
                if (i < 0 || i > 2) {
                    throw new IllegalArgumentException("invalid encoding value: " + i);
                }
                c0019h0.d = i;
                AbstractC0045v abstractC0045v = this.e;
                if (i != 1) {
                    if (i == 2 && !AbstractC0006c.class.isInstance(abstractC0045v)) {
                        throw new IllegalStateException("unexpected object: ".concat(abstractC0045v.getClass().getName()));
                    }
                } else if (!r.class.isInstance(abstractC0045v)) {
                    throw new IllegalStateException("unexpected object: ".concat(abstractC0045v.getClass().getName()));
                }
                c0019h0.e = abstractC0045v;
                return c0019h0;
        }
    }

    @Override // defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        int i = this.f;
        return this;
    }

    /* JADX WARN: Type inference failed for: r1v12, types: [y, E0] */
    /* JADX WARN: Type inference failed for: r1v5, types: [y, r0] */
    public final AbstractC0051y r() {
        switch (this.f) {
            case 0:
                C0018h c0018h = new C0018h(4);
                C0036q c0036q = this.a;
                if (c0036q != null) {
                    c0018h.a(c0036q);
                }
                C0028m c0028m = this.b;
                if (c0028m != null) {
                    c0018h.a(c0028m);
                }
                AbstractC0045v abstractC0045v = this.c;
                if (abstractC0045v != null) {
                    c0018h.a(abstractC0045v.p());
                }
                int i = this.d;
                c0018h.a(new X(i == 0, i, this.e, 1));
                ?? abstractC0051y = new AbstractC0051y(c0018h);
                abstractC0051y.c = -1;
                return abstractC0051y;
            default:
                C0018h c0018h2 = new C0018h(4);
                C0036q c0036q2 = this.a;
                if (c0036q2 != null) {
                    c0018h2.a(c0036q2);
                }
                C0028m c0028m2 = this.b;
                if (c0028m2 != null) {
                    c0018h2.a(c0028m2);
                }
                AbstractC0045v abstractC0045v2 = this.c;
                if (abstractC0045v2 != null) {
                    c0018h2.a(abstractC0045v2.q());
                }
                int i2 = this.d;
                c0018h2.a(new X(i2 == 0, i2, this.e, 2));
                ?? abstractC0051y2 = new AbstractC0051y(c0018h2);
                abstractC0051y2.c = -1;
                return abstractC0051y2;
        }
    }
}
