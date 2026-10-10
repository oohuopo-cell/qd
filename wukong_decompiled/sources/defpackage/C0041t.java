package defpackage;

import java.io.OutputStream;

/* renamed from: t, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public class C0041t {
    public final OutputStream a;

    public C0041t(OutputStream outputStream) {
        this.a = outputStream;
    }

    public static int c(int i) {
        if (i < 128) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 8;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public static int d(boolean z, int i) {
        return c(i) + (z ? 1 : 0) + i;
    }

    public static int e(int i) {
        if (i < 31) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 7;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [p0, t] */
    public C0035p0 a() {
        return new C0041t(this.a);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [t, D0] */
    public D0 b() {
        return new C0041t(this.a);
    }

    public final void f(int i) {
        this.a.write(i);
    }

    public final void g(byte[] bArr, int i, int i2) {
        this.a.write(bArr, i, i2);
    }

    public final void h(int i) {
        if (i < 128) {
            f(i);
            return;
        }
        int i2 = 5;
        byte[] bArr = new byte[5];
        while (true) {
            int i3 = i2 - 1;
            bArr[i3] = (byte) i;
            i >>>= 8;
            if (i == 0) {
                int i4 = i2 - 2;
                bArr[i4] = (byte) ((5 - i3) | 128);
                g(bArr, i4, 6 - i3);
                return;
            }
            i2 = i3;
        }
    }

    public void i(InterfaceC0016g[] interfaceC0016gArr) {
        for (InterfaceC0016g interfaceC0016g : interfaceC0016gArr) {
            interfaceC0016g.e().j(this, true);
        }
    }

    public final void j(boolean z, int i, byte[] bArr) {
        m(z, i);
        h(bArr.length);
        g(bArr, 0, bArr.length);
    }

    public final void k(boolean z, int i, InterfaceC0016g[] interfaceC0016gArr) {
        m(z, i);
        f(128);
        i(interfaceC0016gArr);
        f(0);
        f(0);
    }

    public final void l(int i, int i2) {
        if (i2 < 31) {
            f(i | i2);
            return;
        }
        byte[] bArr = new byte[6];
        int i3 = 5;
        bArr[5] = (byte) (i2 & 127);
        while (i2 > 127) {
            i2 >>>= 7;
            i3--;
            bArr[i3] = (byte) ((i2 & 127) | 128);
        }
        int i4 = i3 - 1;
        bArr[i4] = (byte) (i | 31);
        g(bArr, i4, 6 - i4);
    }

    public final void m(boolean z, int i) {
        if (z) {
            f(i);
        }
    }

    public void n(AbstractC0045v abstractC0045v) {
        abstractC0045v.j(this, true);
    }

    public void o(AbstractC0045v[] abstractC0045vArr) {
        for (AbstractC0045v abstractC0045v : abstractC0045vArr) {
            abstractC0045v.j(this, true);
        }
    }
}
