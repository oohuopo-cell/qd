package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: c0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0007c0 extends InputStream {
    public final A a;
    public boolean b = true;
    public int c = 0;
    public InterfaceC0009d d;
    public InputStream e;

    public C0007c0(A a) {
        this.a = a;
    }

    public final InterfaceC0009d a() {
        A a = this.a;
        int read = a.a.read();
        InterfaceC0016g a2 = read < 0 ? null : a.a(read);
        if (a2 == null) {
            return null;
        }
        if (a2 instanceof InterfaceC0009d) {
            if (this.c == 0) {
                return (InterfaceC0009d) a2;
            }
            throw new IOException("only the last nested bitstring can have padding");
        }
        throw new IOException("unknown object encountered: " + a2.getClass());
    }

    @Override // java.io.InputStream
    public final int read() {
        if (this.e == null) {
            if (this.b) {
                InterfaceC0009d a = a();
                this.d = a;
                if (a != null) {
                    this.b = false;
                    this.e = a.d();
                }
            }
            return -1;
        }
        while (true) {
            int read = this.e.read();
            if (read >= 0) {
                return read;
            }
            this.c = this.d.f();
            InterfaceC0009d a2 = a();
            this.d = a2;
            if (a2 == null) {
                this.e = null;
                return -1;
            }
            this.e = a2.d();
        }
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (this.e == null) {
            if (!this.b) {
                return -1;
            }
            InterfaceC0009d a = a();
            this.d = a;
            if (a == null) {
                return -1;
            }
            this.b = false;
            this.e = a.d();
        }
        while (true) {
            int read = this.e.read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                this.c = this.d.f();
                InterfaceC0009d a2 = a();
                this.d = a2;
                if (a2 == null) {
                    this.e = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this.e = a2.d();
            }
        }
    }
}
