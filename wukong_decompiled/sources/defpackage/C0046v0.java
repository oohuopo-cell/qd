package defpackage;

import java.util.Arrays;

/* renamed from: v0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0046v0 extends AbstractC0045v implements B {
    public final byte[] a;

    public C0046v0(byte[] bArr) {
        this.a = bArr;
    }

    @Override // defpackage.B
    public final String a() {
        int i = u1.a;
        byte[] bArr = this.a;
        int length = bArr.length;
        char[] cArr = new char[length];
        short[] sArr = z1.a;
        int i2 = 0;
        int i3 = 0;
        loop0: while (i2 < bArr.length) {
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b < 0) {
                short s = z1.a[b & Byte.MAX_VALUE];
                int i5 = s >>> 8;
                byte b2 = (byte) s;
                while (true) {
                    if (b2 >= 0) {
                        if (i4 >= bArr.length) {
                            break loop0;
                        }
                        int i6 = i4 + 1;
                        byte b3 = bArr[i4];
                        i5 = (i5 << 6) | (b3 & 63);
                        b2 = z1.b[b2 + ((b3 & 255) >>> 4)];
                        i4 = i6;
                    } else if (b2 != -2) {
                        if (i5 <= 65535) {
                            if (i3 < length) {
                                cArr[i3] = (char) i5;
                                i3++;
                                i2 = i4;
                            }
                        } else if (i3 < length - 1) {
                            int i7 = i3 + 1;
                            cArr[i3] = (char) ((i5 >>> 10) + 55232);
                            i3 += 2;
                            cArr[i7] = (char) ((i5 & 1023) | 56320);
                            i2 = i4;
                        }
                    }
                }
                i3 = -1;
                break;
            }
            if (i3 >= length) {
                i3 = -1;
                break;
            }
            cArr[i3] = (char) b;
            i2 = i4;
            i3++;
        }
        if (i3 >= 0) {
            return new String(cArr, 0, i3);
        }
        throw new IllegalArgumentException("Invalid UTF-8 input");
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C0046v0)) {
            return false;
        }
        return Arrays.equals(this.a, ((C0046v0) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 12, this.a);
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
