package defpackage;

import java.util.Enumeration;

/* loaded from: assets/cp.dat */
public final class v1 extends AbstractC0030n {
    public E a;
    public C0017g0 b;

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, v1] */
    public static v1 i(Object obj) {
        if (obj instanceof v1) {
            return (v1) obj;
        }
        if (obj == null) {
            return null;
        }
        AbstractC0051y t = AbstractC0051y.t(obj);
        ?? obj2 = new Object();
        if (t.w() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + t.w());
        }
        Enumeration v = t.v();
        obj2.a = E.i(v.nextElement());
        obj2.b = C0017g0.t(v.nextElement());
        return obj2;
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
