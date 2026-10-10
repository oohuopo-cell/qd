package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* renamed from: y, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0051y extends AbstractC0045v implements Iterable {
    public static final C0003b b = new C0003b(AbstractC0051y.class, 6);
    public InterfaceC0016g[] a;

    public AbstractC0051y() {
        this.a = C0018h.d;
    }

    public AbstractC0051y(C0018h c0018h) {
        if (c0018h == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        this.a = c0018h.c();
    }

    public AbstractC0051y(AbstractC0045v abstractC0045v) {
        if (abstractC0045v == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        this.a = new InterfaceC0016g[]{abstractC0045v};
    }

    public AbstractC0051y(InterfaceC0016g[] interfaceC0016gArr) {
        this.a = interfaceC0016gArr;
    }

    public static AbstractC0051y t(Object obj) {
        if (obj == null || (obj instanceof AbstractC0051y)) {
            return (AbstractC0051y) obj;
        }
        if (obj instanceof InterfaceC0016g) {
            AbstractC0045v e = ((InterfaceC0016g) obj).e();
            if (e instanceof AbstractC0051y) {
                return (AbstractC0051y) e;
            }
        } else if (obj instanceof byte[]) {
            try {
                C0003b c0003b = b;
                AbstractC0045v o = AbstractC0045v.o((byte[]) obj);
                c0003b.a(o);
                return (AbstractC0051y) o;
            } catch (IOException e2) {
                throw new IllegalArgumentException("failed to construct sequence from byte[]: " + e2.getMessage());
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: ".concat(obj.getClass().getName()));
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public int hashCode() {
        int length = this.a.length;
        int i = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i;
            }
            i = (i * 257) ^ this.a[length].e().hashCode();
        }
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (abstractC0045v instanceof AbstractC0051y) {
            AbstractC0051y abstractC0051y = (AbstractC0051y) abstractC0045v;
            int w = w();
            if (abstractC0051y.w() == w) {
                for (int i = 0; i < w; i++) {
                    AbstractC0045v e = this.a[i].e();
                    AbstractC0045v e2 = abstractC0051y.a[i].e();
                    if (e == e2 || e.i(e2)) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Iterator iterator() {
        return new G(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [v, y, r0] */
    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        ?? abstractC0051y = new AbstractC0051y(this.a);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [v, y, E0] */
    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        ?? abstractC0051y = new AbstractC0051y(this.a);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }

    public final AbstractC0006c[] r() {
        int w = w();
        AbstractC0006c[] abstractC0006cArr = new AbstractC0006c[w];
        for (int i = 0; i < w; i++) {
            abstractC0006cArr[i] = AbstractC0006c.s(this.a[i]);
        }
        return abstractC0006cArr;
    }

    public final r[] s() {
        int w = w();
        r[] rVarArr = new r[w];
        for (int i = 0; i < w; i++) {
            rVarArr[i] = r.r(this.a[i]);
        }
        return rVarArr;
    }

    public final String toString() {
        int w = w();
        if (w == 0) {
            return "[]";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 0;
        while (true) {
            stringBuffer.append(this.a[i]);
            i++;
            if (i >= w) {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
            stringBuffer.append(", ");
        }
    }

    public InterfaceC0016g u(int i) {
        return this.a[i];
    }

    public Enumeration v() {
        return new C0049x(this);
    }

    public int w() {
        return this.a.length;
    }

    public abstract AbstractC0006c x();

    public abstract r y();

    public abstract AbstractC0053z z();
}
