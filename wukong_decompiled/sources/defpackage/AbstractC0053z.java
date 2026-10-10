package defpackage;

import java.io.IOException;
import java.util.Iterator;

/* renamed from: z, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0053z extends AbstractC0045v implements Iterable {
    public final InterfaceC0016g[] a;
    public final boolean b;

    static {
        new C0003b(AbstractC0053z.class, 7);
    }

    public AbstractC0053z() {
        this.a = C0018h.d;
        this.b = true;
    }

    public AbstractC0053z(C0018h c0018h) {
        if (c0018h == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        InterfaceC0016g[] c = c0018h.c();
        this.a = c;
        this.b = c.length < 2;
    }

    public AbstractC0053z(AbstractC0045v abstractC0045v) {
        this.a = new InterfaceC0016g[]{abstractC0045v};
        this.b = true;
    }

    public AbstractC0053z(boolean z, InterfaceC0016g[] interfaceC0016gArr) {
        this.a = interfaceC0016gArr;
        this.b = z || interfaceC0016gArr.length < 2;
    }

    public static byte[] r(InterfaceC0016g interfaceC0016g) {
        try {
            return interfaceC0016g.e().h();
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static boolean s(byte[] bArr, byte[] bArr2) {
        int i = bArr[0] & (-33);
        int i2 = bArr2[0] & (-33);
        if (i != i2) {
            return i < i2;
        }
        int min = Math.min(bArr.length, bArr2.length) - 1;
        for (int i3 = 1; i3 < min; i3++) {
            byte b = bArr[i3];
            byte b2 = bArr2[i3];
            if (b != b2) {
                return (b & 255) < (b2 & 255);
            }
        }
        return (bArr[min] & 255) <= (bArr2[min] & 255);
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        int length = interfaceC0016gArr.length;
        int i = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i;
            }
            i += interfaceC0016gArr[length].e().hashCode();
        }
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (abstractC0045v instanceof AbstractC0053z) {
            AbstractC0053z abstractC0053z = (AbstractC0053z) abstractC0045v;
            int length = this.a.length;
            if (abstractC0053z.a.length == length) {
                C0040s0 c0040s0 = (C0040s0) p();
                C0040s0 c0040s02 = (C0040s0) abstractC0053z.p();
                for (int i = 0; i < length; i++) {
                    AbstractC0045v e = c0040s0.a[i].e();
                    AbstractC0045v e2 = c0040s02.a[i].e();
                    if (e == e2 || e.i(e2)) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        return new G(interfaceC0016gArr.length < 1 ? C0018h.d : (InterfaceC0016g[]) interfaceC0016gArr.clone());
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [v, z, s0] */
    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        int i;
        boolean z = this.b;
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        if (!z) {
            interfaceC0016gArr = (InterfaceC0016g[]) interfaceC0016gArr.clone();
            int length = interfaceC0016gArr.length;
            if (length >= 2) {
                InterfaceC0016g interfaceC0016g = interfaceC0016gArr[0];
                InterfaceC0016g interfaceC0016g2 = interfaceC0016gArr[1];
                byte[] r = r(interfaceC0016g);
                byte[] r2 = r(interfaceC0016g2);
                if (s(r2, r)) {
                    interfaceC0016g2 = interfaceC0016g;
                    interfaceC0016g = interfaceC0016g2;
                    r2 = r;
                    r = r2;
                }
                for (int i2 = 2; i2 < length; i2++) {
                    InterfaceC0016g interfaceC0016g3 = interfaceC0016gArr[i2];
                    byte[] r3 = r(interfaceC0016g3);
                    if (s(r2, r3)) {
                        interfaceC0016gArr[i2 - 2] = interfaceC0016g;
                        interfaceC0016g = interfaceC0016g2;
                        r = r2;
                        interfaceC0016g2 = interfaceC0016g3;
                        r2 = r3;
                    } else if (s(r, r3)) {
                        interfaceC0016gArr[i2 - 2] = interfaceC0016g;
                        interfaceC0016g = interfaceC0016g3;
                        r = r3;
                    } else {
                        int i3 = i2 - 1;
                        while (true) {
                            i = i3 - 1;
                            if (i <= 0) {
                                break;
                            }
                            InterfaceC0016g interfaceC0016g4 = interfaceC0016gArr[i3 - 2];
                            if (s(r(interfaceC0016g4), r3)) {
                                break;
                            }
                            interfaceC0016gArr[i] = interfaceC0016g4;
                            i3 = i;
                        }
                        interfaceC0016gArr[i] = interfaceC0016g3;
                    }
                }
                interfaceC0016gArr[length - 2] = interfaceC0016g;
                interfaceC0016gArr[length - 1] = interfaceC0016g2;
            }
        }
        ?? abstractC0053z = new AbstractC0053z(true, interfaceC0016gArr);
        abstractC0053z.c = -1;
        return abstractC0053z;
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        return new F0(this.b, this.a);
    }

    public final String toString() {
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        int length = interfaceC0016gArr.length;
        if (length == 0) {
            return "[]";
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 0;
        while (true) {
            stringBuffer.append(interfaceC0016gArr[i]);
            i++;
            if (i >= length) {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
            stringBuffer.append(", ");
        }
    }
}
