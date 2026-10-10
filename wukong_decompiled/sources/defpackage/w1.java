package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: assets/cp.dat */
public final class w1 extends AbstractC0030n {
    public AbstractC0051y a;
    public C0028m b;
    public C0028m c;
    public E d;
    public B1 e;
    public y1 f;
    public y1 g;
    public B1 h;
    public v1 i;
    public C0017g0 j;
    public C0017g0 k;
    public P0 l;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v6, types: [P0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v2, types: [O0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object, w1] */
    public static w1 i(InterfaceC0016g interfaceC0016g) {
        int i;
        boolean z;
        boolean z2;
        P0 p0;
        O0 o0;
        C0012e c0012e;
        int i2 = 3;
        if (interfaceC0016g instanceof w1) {
            return (w1) interfaceC0016g;
        }
        if (interfaceC0016g == null) {
            return null;
        }
        AbstractC0051y t = AbstractC0051y.t(interfaceC0016g);
        ?? obj = new Object();
        obj.a = t;
        if (t.u(0) instanceof X) {
            obj.b = (C0028m) C0028m.c.c((X) t.u(0), true);
            i = 0;
        } else {
            obj.b = new C0028m(0L);
            i = -1;
        }
        if (obj.b.t(0)) {
            z = true;
            z2 = false;
        } else if (obj.b.t(1)) {
            z2 = true;
            z = false;
        } else {
            if (!obj.b.t(2)) {
                throw new IllegalArgumentException("version number not recognised");
            }
            z = false;
            z2 = false;
        }
        obj.c = C0028m.r(t.u(i + 1));
        obj.d = E.i(t.u(i + 2));
        obj.e = B1.i(t.u(i + 3));
        AbstractC0051y abstractC0051y = (AbstractC0051y) t.u(i + 4);
        obj.f = y1.i(abstractC0051y.u(0));
        obj.g = y1.i(abstractC0051y.u(1));
        obj.h = B1.i(t.u(i + 5));
        int i3 = i + 6;
        obj.i = v1.i(t.u(i3));
        int w = (t.w() - i3) - 1;
        if (w != 0 && z) {
            throw new IllegalArgumentException("version 1 certificate contains extra data");
        }
        while (w > 0) {
            X x = (X) t.u(i3 + w);
            int i4 = x.c;
            if (i4 == 1) {
                AbstractC0045v s = x.s();
                obj.j = s instanceof C0017g0 ? C0017g0.t(s) : new AbstractC0006c(r.r(s).a, true);
            } else if (i4 == 2) {
                AbstractC0045v s2 = x.s();
                obj.k = s2 instanceof C0017g0 ? C0017g0.t(s2) : new AbstractC0006c(r.r(s2).a, true);
            } else {
                if (i4 != i2) {
                    throw new IllegalArgumentException("Unknown tag encountered in structure: " + x.c);
                }
                if (z2) {
                    throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                }
                AbstractC0051y abstractC0051y2 = (AbstractC0051y) AbstractC0051y.b.c(x, true);
                if (abstractC0051y2 != null) {
                    AbstractC0051y t2 = AbstractC0051y.t(abstractC0051y2);
                    p0 = new Object();
                    p0.a = new Hashtable();
                    p0.b = new Vector();
                    Enumeration v = t2.v();
                    while (v.hasMoreElements()) {
                        Object nextElement = v.nextElement();
                        C0036q c0036q = O0.d;
                        if (nextElement instanceof O0) {
                            o0 = (O0) nextElement;
                        } else if (nextElement != null) {
                            AbstractC0051y t3 = AbstractC0051y.t(nextElement);
                            ?? obj2 = new Object();
                            if (t3.w() == 2) {
                                obj2.a = C0036q.t(t3.u(0));
                                obj2.b = false;
                                obj2.c = r.r(t3.u(1));
                            } else {
                                if (t3.w() != i2) {
                                    throw new IllegalArgumentException("Bad sequence size: " + t3.w());
                                }
                                obj2.a = C0036q.t(t3.u(0));
                                Object u = t3.u(1);
                                if (u == null || (u instanceof C0012e)) {
                                    c0012e = (C0012e) u;
                                } else {
                                    if (!(u instanceof byte[])) {
                                        throw new IllegalArgumentException("illegal object in getInstance: ".concat(u.getClass().getName()));
                                    }
                                    try {
                                        AbstractC0045v o = AbstractC0045v.o((byte[]) u);
                                        if (!C0012e.class.isInstance(o)) {
                                            throw new IllegalStateException("unexpected object: ".concat(o.getClass().getName()));
                                        }
                                        c0012e = (C0012e) o;
                                    } catch (IOException e) {
                                        throw new IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
                                    }
                                }
                                obj2.b = c0012e.s();
                                obj2.c = r.r(t3.u(2));
                            }
                            o0 = obj2;
                        } else {
                            o0 = null;
                        }
                        boolean containsKey = p0.a.containsKey(o0.a);
                        C0036q c0036q2 = o0.a;
                        if (containsKey) {
                            throw new IllegalArgumentException("repeated extension found: " + c0036q2);
                        }
                        p0.a.put(c0036q2, o0);
                        p0.b.addElement(c0036q2);
                        i2 = 3;
                    }
                } else {
                    p0 = 0;
                }
                obj.l = p0;
            }
            w--;
            i2 = 3;
        }
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [v, y, r0] */
    /* JADX WARN: Type inference failed for: r2v4, types: [g, y, r0] */
    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        String a = n1.a("org.bouncycastle.x509.allow_non-der_tbscert");
        AbstractC0051y abstractC0051y = this.a;
        if (a == null || n1.b("org.bouncycastle.x509.allow_non-der_tbscert")) {
            return abstractC0051y;
        }
        C0018h c0018h = new C0018h();
        C0028m c0028m = this.b;
        if (!c0028m.t(0)) {
            c0018h.a(new X(true, 0, (InterfaceC0016g) c0028m, 1));
        }
        c0018h.a(this.c);
        c0018h.a(this.d);
        c0018h.a(this.e);
        C0018h c0018h2 = new C0018h(2);
        c0018h2.a(this.f);
        c0018h2.a(this.g);
        ?? abstractC0051y2 = new AbstractC0051y(c0018h2);
        abstractC0051y2.c = -1;
        c0018h.a(abstractC0051y2);
        InterfaceC0016g interfaceC0016g = this.h;
        if (interfaceC0016g == null) {
            interfaceC0016g = new C0038r0();
        }
        c0018h.a(interfaceC0016g);
        c0018h.a(this.i);
        C0017g0 c0017g0 = this.j;
        if (c0017g0 != null) {
            c0018h.a(new X(2, 128, 1, c0017g0, 1));
        }
        C0017g0 c0017g02 = this.k;
        if (c0017g02 != null) {
            c0018h.a(new X(2, 128, 2, c0017g02, 1));
        }
        P0 p0 = this.l;
        if (p0 != null) {
            c0018h.a(new X(true, 3, (InterfaceC0016g) p0, 1));
        }
        ?? abstractC0051y3 = new AbstractC0051y(c0018h);
        abstractC0051y3.c = -1;
        return abstractC0051y3;
    }
}
