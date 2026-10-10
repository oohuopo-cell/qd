package defpackage;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: assets/cp.dat */
public final class X extends AbstractC0045v implements W0 {
    public final int a;
    public final int b;
    public final int c;
    public final InterfaceC0016g d;
    public final /* synthetic */ int e;

    public X(int i, int i2, int i3, InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        if (i2 == 0 || (i2 & 192) != i2) {
            throw new IllegalArgumentException("invalid tag class: " + i2);
        }
        this.a = interfaceC0016g instanceof InterfaceC0014f ? 1 : i;
        this.b = i2;
        this.c = i3;
        this.d = interfaceC0016g;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ X(int i, int i2, int i3, InterfaceC0016g interfaceC0016g, int i4) {
        this(i, i2, i3, interfaceC0016g);
        this.e = i4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public X(boolean z, int i, InterfaceC0016g interfaceC0016g, int i2) {
        this(z ? 1 : 2, 128, i, interfaceC0016g);
        this.e = i2;
    }

    public static AbstractC0045v r(int i, int i2, C0018h c0018h) {
        int i3;
        X x;
        if (c0018h.b == 1) {
            i3 = i;
            x = new X(3, i3, i2, c0018h.b(0), 2);
        } else {
            i3 = i;
            x = new X(4, i, i2, C0.a(c0018h), 2);
        }
        return i3 != 64 ? x : new AbstractC0000a(x);
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return this;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return (((this.b * 7919) ^ this.c) ^ (t() ? 15 : 240)) ^ this.d.e().hashCode();
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (abstractC0045v instanceof AbstractC0000a) {
            return abstractC0045v.n(this);
        }
        if (!(abstractC0045v instanceof X)) {
            return false;
        }
        X x = (X) abstractC0045v;
        if (this.c != x.c || this.b != x.b) {
            return false;
        }
        if (this.a != x.a && t() != x.t()) {
            return false;
        }
        AbstractC0045v e = this.d.e();
        AbstractC0045v e2 = x.d.e();
        if (e == e2) {
            return true;
        }
        if (t()) {
            return e.i(e2);
        }
        try {
            return Arrays.equals(g(), x.g());
        } catch (IOException unused) {
            return false;
        }
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        switch (this.e) {
            case 0:
                AbstractC0045v e = this.d.e();
                boolean t = t();
                if (z) {
                    int i = this.b;
                    if (t || e.k()) {
                        i |= 32;
                    }
                    c0041t.l(i, this.c);
                }
                if (!t) {
                    e.j(c0041t, false);
                    return;
                }
                c0041t.f(128);
                e.j(c0041t, true);
                c0041t.f(0);
                c0041t.f(0);
                return;
            case 1:
                AbstractC0045v p = this.d.e().p();
                boolean t2 = t();
                if (z) {
                    int i2 = this.b;
                    if (t2 || p.k()) {
                        i2 |= 32;
                    }
                    c0041t.l(i2, this.c);
                }
                if (t2) {
                    c0041t.h(p.m(true));
                }
                p.j(c0041t.a(), t2);
                return;
            default:
                AbstractC0045v q = this.d.e().q();
                boolean t3 = t();
                if (z) {
                    int i3 = this.b;
                    if (t3 || q.k()) {
                        i3 |= 32;
                    }
                    c0041t.l(i3, this.c);
                }
                if (t3) {
                    c0041t.h(q.m(true));
                }
                q.j(c0041t.b(), t3);
                return;
        }
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        switch (this.e) {
            case 0:
                return t() || this.d.e().k();
            case 1:
                return t() || this.d.e().p().k();
            default:
                return t() || this.d.e().q().k();
        }
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        switch (this.e) {
            case 0:
                AbstractC0045v e = this.d.e();
                boolean t = t();
                int m = e.m(t);
                if (t) {
                    m += 3;
                }
                return m + (z ? C0041t.e(this.c) : 0);
            case 1:
                AbstractC0045v p = this.d.e().p();
                boolean t2 = t();
                int m2 = p.m(t2);
                if (t2) {
                    m2 += C0041t.c(m2);
                }
                return m2 + (z ? C0041t.e(this.c) : 0);
            default:
                AbstractC0045v q = this.d.e().q();
                boolean t3 = t();
                int m3 = q.m(t3);
                if (t3) {
                    m3 += C0041t.c(m3);
                }
                return m3 + (z ? C0041t.e(this.c) : 0);
        }
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        switch (this.e) {
            case 1:
                return this;
            default:
                return new X(this.a, this.b, this.c, this.d, 1);
        }
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        switch (this.e) {
            case 1:
            case 2:
                return this;
            default:
                return new X(this.a, this.b, this.c, this.d, 2);
        }
    }

    public final AbstractC0045v s() {
        if (128 == this.b) {
            return this.d.e();
        }
        throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
    }

    public final boolean t() {
        int i = this.a;
        return i == 1 || i == 3;
    }

    public final String toString() {
        return D.e(this.b, this.c) + this.d;
    }
}
