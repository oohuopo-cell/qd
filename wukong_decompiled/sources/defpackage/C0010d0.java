package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: d0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0010d0 extends InputStream {
    public final A a;
    public boolean b = true;
    public InputStream c;

    public C0010d0(A a) {
        this.a = a;
    }

    public final InterfaceC0039s a() {
        A a = this.a;
        int read = a.a.read();
        InterfaceC0016g a2 = read < 0 ? null : a.a(read);
        if (a2 == null) {
            return null;
        }
        if (a2 instanceof InterfaceC0039s) {
            return (InterfaceC0039s) a2;
        }
        throw new IOException("unknown object encountered: " + a2.getClass());
    }

    @Override // java.io.InputStream
    public final int read() {
        InterfaceC0039s a;
        if (this.c == null) {
            if (!this.b || (a = a()) == null) {
                return -1;
            }
            this.b = false;
            this.c = a.c();
        }
        while (true) {
            int read = this.c.read();
            if (read >= 0) {
                return read;
            }
            InterfaceC0039s a2 = a();
            if (a2 == null) {
                this.c = null;
                return -1;
            }
            this.c = a2.c();
        }
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        InterfaceC0039s a;
        int i3 = 0;
        if (this.c == null) {
            if (!this.b || (a = a()) == null) {
                return -1;
            }
            this.b = false;
            this.c = a.c();
        }
        while (true) {
            int read = this.c.read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                InterfaceC0039s a2 = a();
                if (a2 == null) {
                    this.c = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this.c = a2.c();
            }
        }
    }
}
