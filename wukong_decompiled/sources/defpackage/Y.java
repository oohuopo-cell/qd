package defpackage;

import java.io.IOException;

/* loaded from: assets/cp.dat */
public class Y implements InterfaceC0016g, W0 {
    public final int a;
    public final int b;
    public final A c;

    public Y(int i, int i2, A a) {
        this.a = i;
        this.b = i2;
        this.c = a;
    }

    public AbstractC0045v b() {
        return this.c.b(this.a, this.b);
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        try {
            return b();
        } catch (IOException e) {
            throw new C0043u(e.getMessage(), 0);
        }
    }
}
