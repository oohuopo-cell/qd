package defpackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: assets/cp.dat */
public final class P implements InterfaceC0009d {
    public final A a;
    public C0007c0 b;

    public P(A a) {
        this.a = a;
    }

    public static O a(A a) {
        C0007c0 c0007c0 = new C0007c0(a);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = c0007c0.read(bArr, 0, 4096);
            if (read < 0) {
                return new O(byteArrayOutputStream.toByteArray(), c0007c0.c);
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return a(this.a);
    }

    @Override // defpackage.InterfaceC0009d
    public final InputStream d() {
        C0007c0 c0007c0 = new C0007c0(this.a);
        this.b = c0007c0;
        return c0007c0;
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        try {
            return a(this.a);
        } catch (IOException e) {
            throw new C0043u("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }

    @Override // defpackage.InterfaceC0009d
    public final int f() {
        return this.b.c;
    }
}
