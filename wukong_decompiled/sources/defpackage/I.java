package defpackage;

/* loaded from: assets/cp.dat */
public final class I extends AbstractC0030n {
    public C0036q a;
    public InterfaceC0016g b;

    /* JADX WARN: Type inference failed for: r0v2, types: [I, java.lang.Object] */
    public static I i(InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g instanceof I) {
            return (I) interfaceC0016g;
        }
        if (interfaceC0016g == null) {
            throw new IllegalArgumentException("null value in getInstance()");
        }
        AbstractC0051y t = AbstractC0051y.t(interfaceC0016g);
        ?? obj = new Object();
        obj.a = (C0036q) t.u(0);
        obj.b = t.u(1);
        return obj;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [v, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        C0018h c0018h = new C0018h(2);
        c0018h.a(this.a);
        c0018h.a(this.b);
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }
}
