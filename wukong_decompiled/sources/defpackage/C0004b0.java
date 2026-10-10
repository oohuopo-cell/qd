package defpackage;

/* renamed from: b0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0004b0 extends AbstractC0030n {
    public AbstractC0051y a;
    public w1 b;

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, b0] */
    public static C0004b0 i(Object obj) {
        if (obj instanceof C0004b0) {
            return (C0004b0) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0051y t = AbstractC0051y.t(obj);
        ?? obj2 = new Object();
        obj2.a = t;
        if (t.w() != 3) {
            throw new IllegalArgumentException("sequence wrong size for a certificate");
        }
        obj2.b = w1.i(t.u(0));
        E.i(t.u(1));
        AbstractC0006c.s(t.u(2));
        return obj2;
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        return this.a;
    }
}
