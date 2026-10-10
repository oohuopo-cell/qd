package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: assets/cp.dat */
public final class B0 implements InterfaceC0009d {
    public final L0 a;
    public int b = 0;

    public B0(L0 l0) {
        this.a = l0;
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return AbstractC0006c.r(this.a.b());
    }

    @Override // defpackage.InterfaceC0009d
    public final InputStream d() {
        L0 l0 = this.a;
        int i = l0.d;
        if (i < 1) {
            throw new IllegalStateException("content octets cannot be empty");
        }
        int read = l0.read();
        this.b = read;
        if (read > 0) {
            if (i < 2) {
                throw new IllegalStateException("zero length data with non-zero pad bits");
            }
            if (read > 7) {
                throw new IllegalStateException("pad bits cannot be greater than 7 or less than 0");
            }
        }
        return l0;
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        try {
            return b();
        } catch (IOException e) {
            throw new C0043u("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }

    @Override // defpackage.InterfaceC0009d
    public final int f() {
        return this.b;
    }
}
