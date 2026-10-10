package defpackage;

/* loaded from: assets/cp.dat */
public final class E extends AbstractC0030n {
    public C0036q a;
    public InterfaceC0016g b;

    public E(C0036q c0036q, InterfaceC0016g interfaceC0016g) {
        this.a = c0036q;
        this.b = interfaceC0016g;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [E, java.lang.Object] */
    public static E i(Object obj) {
        if (obj instanceof E) {
            return (E) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0051y t = AbstractC0051y.t(obj);
        ?? obj2 = new Object();
        if (t.w() < 1 || t.w() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + t.w());
        }
        obj2.a = C0036q.t(t.u(0));
        if (t.w() == 2) {
            obj2.b = t.u(1);
            return obj2;
        }
        obj2.b = null;
        return obj2;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [v, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        C0018h c0018h = new C0018h(2);
        c0018h.a(this.a);
        InterfaceC0016g interfaceC0016g = this.b;
        if (interfaceC0016g != null) {
            c0018h.a(interfaceC0016g);
        }
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }
}
