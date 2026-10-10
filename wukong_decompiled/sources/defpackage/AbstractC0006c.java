package defpackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: c, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0006c extends AbstractC0045v implements B, InterfaceC0009d {
    public static final C0003b b = new C0003b(AbstractC0006c.class, 0);
    public static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final byte[] a;

    public AbstractC0006c(byte[] bArr, int i) {
        if (bArr == null) {
            throw new NullPointerException("'data' cannot be null");
        }
        if (bArr.length == 0 && i != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        }
        if (i > 7 || i < 0) {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 1];
        System.arraycopy(bArr, 0, bArr2, 1, length);
        bArr2[0] = (byte) i;
        this.a = bArr2;
    }

    public AbstractC0006c(byte[] bArr, boolean z) {
        if (z) {
            if (bArr == null) {
                throw new NullPointerException("'contents' cannot be null");
            }
            if (bArr.length < 1) {
                throw new IllegalArgumentException("'contents' cannot be empty");
            }
            int i = bArr[0] & 255;
            if (i > 0) {
                if (bArr.length < 2) {
                    throw new IllegalArgumentException("zero length data with non-zero pad bits");
                }
                if (i > 7) {
                    throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
                }
            }
        }
        this.a = bArr;
    }

    public static AbstractC0006c r(byte[] bArr) {
        int length = bArr.length;
        if (length < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int i = bArr[0] & 255;
        if (i > 0) {
            if (i > 7 || length < 2) {
                throw new IllegalArgumentException("invalid pad bits detected");
            }
            byte b2 = bArr[length - 1];
            if (b2 != ((byte) ((255 << i) & b2))) {
                return new AbstractC0006c(bArr, false);
            }
        }
        return new AbstractC0006c(bArr, false);
    }

    public static AbstractC0006c s(InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g == null || (interfaceC0016g instanceof AbstractC0006c)) {
            return (AbstractC0006c) interfaceC0016g;
        }
        AbstractC0045v e = interfaceC0016g.e();
        if (e instanceof AbstractC0006c) {
            return (AbstractC0006c) e;
        }
        throw new IllegalArgumentException("illegal object in getInstance: ".concat(interfaceC0016g.getClass().getName()));
    }

    @Override // defpackage.B
    public final String a() {
        try {
            byte[] g = g();
            StringBuffer stringBuffer = new StringBuffer((g.length * 2) + 1);
            stringBuffer.append('#');
            for (int i = 0; i != g.length; i++) {
                byte b2 = g[i];
                char[] cArr = c;
                stringBuffer.append(cArr[(b2 >>> 4) & 15]);
                stringBuffer.append(cArr[b2 & 15]);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new C0043u("Internal error encoding BitString: " + e.getMessage(), e);
        }
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return this;
    }

    @Override // defpackage.InterfaceC0009d
    public final InputStream d() {
        byte[] bArr = this.a;
        return new ByteArrayInputStream(bArr, 1, bArr.length - 1);
    }

    @Override // defpackage.InterfaceC0009d
    public final int f() {
        return this.a[0] & 255;
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        byte[] bArr = this.a;
        if (bArr.length < 2) {
            return 1;
        }
        int i = 0;
        int i2 = bArr[0] & 255;
        int length = bArr.length;
        int i3 = length - 1;
        byte b2 = (byte) ((255 << i2) & bArr[i3]);
        if (bArr != null) {
            i = length;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                i = (i * 257) ^ bArr[i3];
            }
        }
        return (i * 257) ^ b2;
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof AbstractC0006c)) {
            return false;
        }
        byte[] bArr = ((AbstractC0006c) abstractC0045v).a;
        byte[] bArr2 = this.a;
        int length = bArr2.length;
        if (bArr.length != length) {
            return false;
        }
        if (length == 1) {
            return true;
        }
        int i = length - 1;
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr2[i2] != bArr[i2]) {
                return false;
            }
        }
        int i3 = 255 << (bArr2[0] & 255);
        return ((byte) (bArr2[i] & i3)) == ((byte) (bArr[i] & i3));
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        return new AbstractC0006c(this.a, false);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        return new AbstractC0006c(this.a, false);
    }

    public final String toString() {
        return a();
    }
}
