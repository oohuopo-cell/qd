package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: assets/cp.dat */
public final class L0 extends e1 {
    public static final byte[] e = new byte[0];
    public final int c;
    public int d;

    public L0(InputStream inputStream, int i, int i2) {
        super(inputStream, i2);
        if (i <= 0) {
            if (i < 0) {
                throw new IllegalArgumentException("negative lengths not allowed");
            }
            a();
        }
        this.c = i;
        this.d = i;
    }

    public final byte[] b() {
        int i = this.d;
        if (i == 0) {
            return e;
        }
        int i2 = this.b;
        if (i >= i2) {
            throw new IOException("corrupted stream - out of bounds length found: " + this.d + " >= " + i2);
        }
        byte[] bArr = new byte[i];
        int h = i - D.h(this.a, bArr, i);
        this.d = h;
        if (h == 0) {
            a();
            return bArr;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    @Override // java.io.InputStream
    public final int read() {
        if (this.d == 0) {
            return -1;
        }
        int read = this.a.read();
        if (read >= 0) {
            int i = this.d - 1;
            this.d = i;
            if (i == 0) {
                a();
            }
            return read;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        int i3 = this.d;
        if (i3 == 0) {
            return -1;
        }
        int read = this.a.read(bArr, i, Math.min(i2, i3));
        if (read >= 0) {
            int i4 = this.d - read;
            this.d = i4;
            if (i4 == 0) {
                a();
            }
            return read;
        }
        throw new EOFException("DEF length " + this.c + " object truncated by " + this.d);
    }
}
