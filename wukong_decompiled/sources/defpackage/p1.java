package defpackage;

/* loaded from: assets/cp.dat */
public final class p1 extends AbstractC0030n {
    public static final E e;
    public static final E f;
    public static final C0028m g;
    public static final C0028m h;
    public E a;
    public E b;
    public C0028m c;
    public C0028m d;

    static {
        E e2 = new E(i1.a, C0029m0.a);
        e = e2;
        f = new E(l1.f, e2);
        g = new C0028m(20L);
        h = new C0028m(1L);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [p1, java.lang.Object] */
    public static p1 i(Object obj) {
        if (obj instanceof p1) {
            return (p1) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0051y t = AbstractC0051y.t(obj);
        ?? obj2 = new Object();
        obj2.a = e;
        obj2.b = f;
        obj2.c = g;
        obj2.d = h;
        for (int i = 0; i != t.w(); i++) {
            X x = (X) t.u(i);
            int i2 = x.c;
            C0003b c0003b = AbstractC0051y.b;
            if (i2 == 0) {
                obj2.a = E.i((AbstractC0051y) c0003b.c(x, true));
            } else if (i2 != 1) {
                C0003b c0003b2 = C0028m.c;
                if (i2 == 2) {
                    obj2.c = (C0028m) c0003b2.c(x, true);
                } else {
                    if (i2 != 3) {
                        throw new IllegalArgumentException("unknown tag");
                    }
                    obj2.d = (C0028m) c0003b2.c(x, true);
                }
            } else {
                obj2.b = E.i((AbstractC0051y) c0003b.c(x, true));
            }
        }
        return obj2;
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [v, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        C0018h c0018h = new C0018h(4);
        E e2 = e;
        E e3 = this.a;
        if (!e3.equals(e2)) {
            c0018h.a(new X(true, 0, (InterfaceC0016g) e3, 1));
        }
        E e4 = f;
        E e5 = this.b;
        if (!e5.equals(e4)) {
            c0018h.a(new X(true, 1, (InterfaceC0016g) e5, 1));
        }
        C0028m c0028m = g;
        C0028m c0028m2 = this.c;
        if (!c0028m2.n(c0028m)) {
            c0018h.a(new X(true, 2, (InterfaceC0016g) c0028m2, 1));
        }
        C0028m c0028m3 = h;
        C0028m c0028m4 = this.d;
        if (!c0028m4.n(c0028m3)) {
            c0018h.a(new X(true, 3, (InterfaceC0016g) c0028m4, 1));
        }
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }
}
