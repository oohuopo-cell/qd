package defpackage;

import java.io.IOException;

/* loaded from: assets/cp.dat */
public final class A {
    public final e1 a;
    public final int b;
    public final byte[][] c;

    public A(e1 e1Var, int i, byte[][] bArr) {
        this.a = e1Var;
        this.b = i;
        this.c = bArr;
    }

    public final InterfaceC0016g a(int i) {
        AbstractC0045v abstractC0045v;
        e1 e1Var = this.a;
        if (e1Var instanceof X0) {
            X0 x0 = (X0) e1Var;
            x0.f = false;
            x0.b();
        }
        int f = C0026l.f(e1Var, i);
        int i2 = 4;
        boolean z = f == 3 || f == 4 || f == 16 || f == 17 || f == 8;
        int i3 = this.b;
        int d = C0026l.d(e1Var, i3, z);
        byte[][] bArr = this.c;
        if (d < 0) {
            if ((i & 32) == 0) {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
            A a = new A(new X0(e1Var, i3), i3, bArr);
            int i4 = i & 192;
            if (i4 != 0) {
                return 64 == i4 ? new Y(64, f, a) : new Y(i4, f, a);
            }
            if (f == 3) {
                return new P(a);
            }
            if (f == 4) {
                return new T(a);
            }
            if (f == 8) {
                return new V(a);
            }
            if (f == 16) {
                V v = new V(0);
                v.b = a;
                return v;
            }
            if (f == 17) {
                V v2 = new V(1);
                v2.b = a;
                return v2;
            }
            throw new IOException("unknown BER object encountered: 0x" + Integer.toHexString(f));
        }
        L0 l0 = new L0(e1Var, d, i3);
        if ((i & 224) == 0) {
            if (f == 3) {
                return new B0(l0);
            }
            if (f == 4) {
                T t = new T();
                t.b = l0;
                return t;
            }
            if (f == 8) {
                throw new IOException("externals must use constructed encoding (see X.690 8.18)");
            }
            if (f == 16) {
                throw new IOException("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
            }
            if (f == 17) {
                throw new IOException("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
            }
            try {
                return C0026l.b(f, l0, bArr);
            } catch (IllegalArgumentException e) {
                throw new C0022j("corrupted stream detected", e);
            }
        }
        A a2 = new A(l0, l0.b, bArr);
        int i5 = i & 192;
        if (i5 != 0) {
            boolean z2 = (i & 32) != 0;
            if (64 != i5) {
                return new G0(i5, f, z2, a2);
            }
            if (z2) {
                abstractC0045v = X.r(i5, f, a2.c());
            } else {
                X x = new X(i2, i5, f, new r(l0.b()), 2);
                abstractC0045v = x;
                if (i5 == 64) {
                    abstractC0045v = new AbstractC0000a(x);
                }
            }
            return (C0054z0) abstractC0045v;
        }
        if (f == 3) {
            return new P(a2);
        }
        if (f == 4) {
            return new T(a2);
        }
        if (f == 8) {
            return new V(a2);
        }
        if (f == 16) {
            V v3 = new V(3);
            v3.b = a2;
            return v3;
        }
        if (f == 17) {
            V v4 = new V(4);
            v4.b = a2;
            return v4;
        }
        throw new IOException("unknown DL object encountered: 0x" + Integer.toHexString(f));
    }

    public final AbstractC0045v b(int i, int i2) {
        int i3;
        X x;
        int i4 = 0;
        C0018h c = c();
        int i5 = c.b;
        if (i5 == 1) {
            i3 = i;
            x = new X(3, i3, i2, c.b(0), 0);
        } else {
            U u = Q.a;
            i3 = i;
            x = new X(4, i, i2, i5 < 1 ? Q.a : new AbstractC0051y(c), 0);
        }
        return i3 != 64 ? x : new M(x, i4);
    }

    public final C0018h c() {
        e1 e1Var = this.a;
        int read = e1Var.read();
        if (read < 0) {
            return new C0018h(0);
        }
        C0018h c0018h = new C0018h();
        do {
            InterfaceC0016g a = a(read);
            c0018h.a(a instanceof W0 ? ((W0) a).b() : a.e());
            read = e1Var.read();
        } while (read >= 0);
        return c0018h;
    }
}
