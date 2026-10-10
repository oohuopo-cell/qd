package defpackage;

import java.util.Arrays;

/* renamed from: w0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0048w0 extends AbstractC0045v implements B {
    public static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final byte[] a;

    public C0048w0(byte[] bArr) {
        this.a = bArr;
    }

    public static void r(StringBuffer stringBuffer, int i) {
        char[] cArr = b;
        stringBuffer.append(cArr[(i >>> 4) & 15]);
        stringBuffer.append(cArr[i & 15]);
    }

    @Override // defpackage.B
    public final String a() {
        int i;
        byte[] bArr = this.a;
        int length = bArr.length;
        StringBuffer stringBuffer = new StringBuffer(((C0041t.c(length) + length) * 2) + 3);
        stringBuffer.append("#1C");
        if (length < 128) {
            r(stringBuffer, length);
        } else {
            byte[] bArr2 = new byte[5];
            int i2 = length;
            int i3 = 5;
            while (true) {
                i = i3 - 1;
                bArr2[i] = (byte) i2;
                i2 >>>= 8;
                if (i2 == 0) {
                    break;
                }
                i3 = i;
            }
            int i4 = i3 - 2;
            bArr2[i4] = (byte) (128 | (5 - i));
            while (true) {
                int i5 = i4 + 1;
                r(stringBuffer, bArr2[i4]);
                if (i5 >= 5) {
                    break;
                }
                i4 = i5;
            }
        }
        for (byte b2 : bArr) {
            r(stringBuffer, b2);
        }
        return stringBuffer.toString();
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0048w0)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0048w0) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 28, this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, this.a.length);
    }

    public final String toString() {
        return a();
    }
}
