package defpackage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: assets/cp.dat */
public final class T implements InterfaceC0039s {
    public final /* synthetic */ int a = 1;
    public Object b;

    public /* synthetic */ T() {
    }

    public T(A a) {
        this.b = a;
    }

    public static S a(A a) {
        C0010d0 c0010d0 = new C0010d0(a);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = c0010d0.read(bArr, 0, 4096);
            if (read < 0) {
                return new S(byteArrayOutputStream.toByteArray(), null);
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        switch (this.a) {
            case 0:
                return a((A) this.b);
            default:
                return new r(((L0) this.b).b());
        }
    }

    @Override // defpackage.InterfaceC0039s
    public final InputStream c() {
        switch (this.a) {
            case 0:
                return new C0010d0((A) this.b);
            default:
                return (L0) this.b;
        }
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        switch (this.a) {
            case 0:
                try {
                    return a((A) this.b);
                } catch (IOException e) {
                    throw new C0043u("IOException converting stream to byte array: " + e.getMessage(), e);
                }
            default:
                try {
                    return b();
                } catch (IOException e2) {
                    throw new C0043u("IOException converting stream to byte array: " + e2.getMessage(), e2);
                }
        }
    }
}
