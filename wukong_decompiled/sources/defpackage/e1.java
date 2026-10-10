package defpackage;

import java.io.InputStream;

/* loaded from: assets/cp.dat */
public abstract class e1 extends InputStream {
    public final InputStream a;
    public final int b;

    public e1(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
    }

    public final void a() {
        InputStream inputStream = this.a;
        if (inputStream instanceof X0) {
            X0 x0 = (X0) inputStream;
            x0.f = true;
            x0.b();
        }
    }
}
