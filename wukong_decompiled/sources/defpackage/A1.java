package defpackage;

/* loaded from: assets/cp.dat */
public final class A1 {
    public X a;
    public C0028m b;
    public E c;
    public B1 d;
    public y1 e;
    public y1 f;
    public B1 g;
    public v1 h;
    public P0 i;
    public boolean j;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [g, y, r0] */
    /* JADX WARN: Type inference failed for: r2v9, types: [g, y, r0] */
    public final w1 a() {
        if (this.b == null || this.c == null || this.d == null || this.e == null || this.f == null || ((this.g == null && !this.j) || this.h == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        C0018h c0018h = new C0018h(10);
        c0018h.a(this.a);
        c0018h.a(this.b);
        c0018h.a(this.c);
        c0018h.a(this.d);
        C0018h c0018h2 = new C0018h(2);
        c0018h2.a(this.e);
        c0018h2.a(this.f);
        ?? abstractC0051y = new AbstractC0051y(c0018h2);
        abstractC0051y.c = -1;
        c0018h.a(abstractC0051y);
        InterfaceC0016g interfaceC0016g = this.g;
        if (interfaceC0016g == null) {
            interfaceC0016g = new C0038r0();
        }
        c0018h.a(interfaceC0016g);
        c0018h.a(this.h);
        P0 p0 = this.i;
        if (p0 != null) {
            c0018h.a(new X(true, 3, (InterfaceC0016g) p0, 1));
        }
        ?? abstractC0051y2 = new AbstractC0051y(c0018h);
        abstractC0051y2.c = -1;
        return w1.i(abstractC0051y2);
    }
}
