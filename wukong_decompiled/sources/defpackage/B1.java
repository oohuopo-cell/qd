package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: assets/cp.dat */
public final class B1 extends AbstractC0030n implements InterfaceC0014f {
    public static final L f = L.d;
    public boolean a;
    public int b;
    public final L c = f;
    public final o1[] d;
    public final C0038r0 e;

    /* JADX WARN: Type inference failed for: r10v1, types: [y, java.lang.Object, r0] */
    /* JADX WARN: Type inference failed for: r6v2, types: [o1, java.lang.Object] */
    public B1(AbstractC0051y abstractC0051y) {
        o1 o1Var;
        AbstractC0053z abstractC0053z;
        this.d = new o1[abstractC0051y.w()];
        Enumeration v = abstractC0051y.v();
        boolean z = true;
        int i = 0;
        while (v.hasMoreElements()) {
            Object nextElement = v.nextElement();
            if (nextElement instanceof o1) {
                o1Var = (o1) nextElement;
            } else if (nextElement != null) {
                if (nextElement instanceof AbstractC0053z) {
                    abstractC0053z = (AbstractC0053z) nextElement;
                } else if (nextElement instanceof InterfaceC0016g) {
                    AbstractC0045v e = ((InterfaceC0016g) nextElement).e();
                    if (!(e instanceof AbstractC0053z)) {
                        throw new IllegalArgumentException("unknown object in getInstance: ".concat(nextElement.getClass().getName()));
                    }
                    abstractC0053z = (AbstractC0053z) e;
                } else {
                    if (!(nextElement instanceof byte[])) {
                        throw new IllegalArgumentException("unknown object in getInstance: ".concat(nextElement.getClass().getName()));
                    }
                    try {
                        AbstractC0045v o = AbstractC0045v.o((byte[]) nextElement);
                        if (!AbstractC0053z.class.isInstance(o)) {
                            throw new IllegalStateException("unexpected object: ".concat(o.getClass().getName()));
                        }
                        abstractC0053z = (AbstractC0053z) o;
                    } catch (IOException e2) {
                        throw new IllegalArgumentException("failed to construct set from byte[]: " + e2.getMessage());
                    }
                }
                ?? obj = new Object();
                obj.a = abstractC0053z;
                o1Var = obj;
            } else {
                o1Var = null;
            }
            z &= o1Var == nextElement;
            this.d[i] = o1Var;
            i++;
        }
        if (z) {
            this.e = (C0038r0) abstractC0051y.p();
            return;
        }
        o1[] o1VarArr = this.d;
        ?? obj2 = new Object();
        if (o1VarArr != null) {
            for (o1 o1Var2 : o1VarArr) {
                if (o1Var2 != null) {
                }
            }
            obj2.a = o1VarArr.length < 1 ? C0018h.d : (InterfaceC0016g[]) o1VarArr.clone();
            obj2.c = -1;
            this.e = obj2;
            return;
        }
        throw new NullPointerException("'elements' cannot be null, or contain null");
    }

    public static B1 i(Object obj) {
        if (obj instanceof B1) {
            return (B1) obj;
        }
        if (obj != null) {
            return new B1(AbstractC0051y.t(obj));
        }
        return null;
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        return this.e;
    }

    @Override // defpackage.AbstractC0030n
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof B1) && !(obj instanceof AbstractC0051y)) {
            return false;
        }
        if (this.e.n(((InterfaceC0016g) obj).e())) {
            return true;
        }
        try {
            L l = this.c;
            B1 b1 = new B1(AbstractC0051y.t(((InterfaceC0016g) obj).e()));
            l.getClass();
            return D.b(this, b1);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // defpackage.AbstractC0030n
    public final int hashCode() {
        if (this.a) {
            return this.b;
        }
        this.a = true;
        this.c.getClass();
        o1[] o1VarArr = (o1[]) this.d.clone();
        int i = 0;
        for (int i2 = 0; i2 != o1VarArr.length; i2++) {
            o1 o1Var = o1VarArr[i2];
            if (o1Var.a.a.length > 1) {
                I[] j = o1Var.j();
                for (int i3 = 0; i3 != j.length; i3++) {
                    i = (i ^ j[i3].a.a.hashCode()) ^ D.c(j[i3].b).hashCode();
                }
            } else {
                i = (i ^ o1Var.i().a.a.hashCode()) ^ D.c(o1VarArr[i2].i().b).hashCode();
            }
        }
        this.b = i;
        return i;
    }

    public final String toString() {
        L l = this.c;
        l.getClass();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (o1 o1Var : (o1[]) this.d.clone()) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            Hashtable hashtable = l.a;
            if (o1Var.a.a.length > 1) {
                I[] j = o1Var.j();
                boolean z2 = true;
                for (int i = 0; i != j.length; i++) {
                    if (z2) {
                        z2 = false;
                    } else {
                        stringBuffer.append('+');
                    }
                    D.a(stringBuffer, j[i], hashtable);
                }
            } else if (o1Var.i() != null) {
                D.a(stringBuffer, o1Var.i(), hashtable);
            }
        }
        return stringBuffer.toString();
    }
}
