package defpackage;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: assets/cp.dat */
public final class P0 extends AbstractC0030n {
    public Hashtable a;
    public Vector b;

    /* JADX WARN: Type inference failed for: r1v2, types: [v, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        Vector vector = this.b;
        C0018h c0018h = new C0018h(vector.size());
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            c0018h.a((O0) this.a.get((C0036q) elements.nextElement()));
        }
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }
}
