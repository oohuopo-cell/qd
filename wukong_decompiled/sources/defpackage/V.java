package defpackage;

import java.io.IOException;

/* loaded from: assets/cp.dat */
public final class V implements InterfaceC0016g, W0 {
    public final /* synthetic */ int a;
    public A b;

    public /* synthetic */ V(int i) {
        this.a = i;
    }

    public V(A a) {
        this.a = 2;
        this.b = a;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [U, y] */
    public static U a(A a) {
        return new AbstractC0051y(a.c());
    }

    public static C0019h0 g(A a) {
        try {
            return new C0019h0(C0.a(a.c()));
        } catch (IllegalArgumentException e) {
            throw new C0022j(e.getMessage(), e);
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [v, z, F0] */
    @Override // defpackage.W0
    public final AbstractC0045v b() {
        switch (this.a) {
            case 0:
                return a(this.b);
            case 1:
                return new AbstractC0053z(this.b.c());
            case 2:
                return g(this.b);
            case 3:
                return C0.a(this.b.c());
            default:
                C0018h c = this.b.c();
                E0 e0 = C0.a;
                if (c.b < 1) {
                    return C0.b;
                }
                ?? abstractC0053z = new AbstractC0053z(c);
                abstractC0053z.c = -1;
                return abstractC0053z;
        }
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        switch (this.a) {
            case 0:
                try {
                    return a(this.b);
                } catch (IOException e) {
                    throw new IllegalStateException(e.getMessage());
                }
            case 1:
                try {
                    return new AbstractC0053z(this.b.c());
                } catch (IOException e2) {
                    throw new C0043u(e2.getMessage(), e2);
                }
            case 2:
                try {
                    return g(this.b);
                } catch (IOException e3) {
                    throw new C0043u("unable to get DER object", e3);
                } catch (IllegalArgumentException e4) {
                    throw new C0043u("unable to get DER object", e4);
                }
            case 3:
                try {
                    return b();
                } catch (IOException e5) {
                    throw new IllegalStateException(e5.getMessage());
                }
            default:
                try {
                    return b();
                } catch (IOException e6) {
                    throw new C0043u(e6.getMessage(), e6);
                }
        }
    }
}
