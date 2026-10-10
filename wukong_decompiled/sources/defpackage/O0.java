package defpackage;

/* loaded from: assets/cp.dat */
public final class O0 extends AbstractC0030n {
    public static final C0036q d = K.a("2.5.29.9");
    public static final C0036q e;
    public static final C0036q f;
    public static final C0036q g;
    public C0036q a;
    public boolean b;
    public r c;

    static {
        new C0036q("2.5.29.14").u();
        new C0036q("2.5.29.15").u();
        new C0036q("2.5.29.16").u();
        e = new C0036q("2.5.29.17").u();
        f = K.a("2.5.29.18");
        new C0036q("2.5.29.19").u();
        new C0036q("2.5.29.20").u();
        new C0036q("2.5.29.21").u();
        new C0036q("2.5.29.23").u();
        new C0036q("2.5.29.24").u();
        new C0036q("2.5.29.27").u();
        new C0036q("2.5.29.28").u();
        g = new C0036q("2.5.29.29").u();
        new C0036q("2.5.29.30").u();
        new C0036q("2.5.29.31").u();
        new C0036q("2.5.29.32").u();
        new C0036q("2.5.29.33").u();
        new C0036q("2.5.29.35").u();
        new C0036q("2.5.29.36").u();
        new C0036q("2.5.29.37").u();
        new C0036q("2.5.29.46").u();
        new C0036q("2.5.29.54").u();
        new C0036q("1.3.6.1.5.5.7.1.1").u();
        new C0036q("1.3.6.1.5.5.7.1.11").u();
        new C0036q("1.3.6.1.5.5.7.1.12").u();
        new C0036q("1.3.6.1.5.5.7.1.2").u();
        new C0036q("1.3.6.1.5.5.7.1.3").u();
        new C0036q("1.3.6.1.5.5.7.1.4").u();
        new C0036q("2.5.29.56").u();
        new C0036q("2.5.29.55").u();
        new C0036q("2.5.29.60").u();
    }

    public O0(C0036q c0036q, boolean z, C0033o0 c0033o0) {
        this.a = c0036q;
        this.b = z;
        this.c = c0033o0;
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [v, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        C0018h c0018h = new C0018h(3);
        c0018h.a(this.a);
        if (this.b) {
            c0018h.a(C0012e.c);
        }
        c0018h.a(this.c);
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }

    @Override // defpackage.AbstractC0030n
    public final boolean equals(Object obj) {
        if (!(obj instanceof O0)) {
            return false;
        }
        O0 o0 = (O0) obj;
        return o0.a.n(this.a) && o0.c.n(this.c) && o0.b == this.b;
    }

    @Override // defpackage.AbstractC0030n
    public final int hashCode() {
        C0036q c0036q = this.a;
        r rVar = this.c;
        if (this.b) {
            return c0036q.a.hashCode() ^ D.f(rVar.a);
        }
        return ~(c0036q.a.hashCode() ^ D.f(rVar.a));
    }
}
