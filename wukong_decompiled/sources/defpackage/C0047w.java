package defpackage;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/* renamed from: w, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0047w extends AbstractC0045v {
    public final String a;
    public byte[] b;

    public C0047w(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        long j = 0;
        BigInteger bigInteger = null;
        for (int i = 0; i != bArr.length; i++) {
            byte b = bArr[i];
            if (j <= 72057594037927808L) {
                long j2 = j + (b & Byte.MAX_VALUE);
                if ((b & 128) == 0) {
                    if (z) {
                        z = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                }
            } else {
                BigInteger or = (bigInteger == null ? BigInteger.valueOf(j) : bigInteger).or(BigInteger.valueOf(b & Byte.MAX_VALUE));
                if ((b & 128) == 0) {
                    if (z) {
                        z = false;
                    } else {
                        stringBuffer.append('.');
                    }
                    stringBuffer.append(or);
                    j = 0;
                    bigInteger = null;
                } else {
                    bigInteger = or.shiftLeft(7);
                }
            }
        }
        this.a = stringBuffer.toString();
        this.b = bArr;
    }

    public static boolean s(String str, int i) {
        int length = str.length();
        int i2 = 0;
        while (true) {
            int i3 = length - 1;
            if (i3 < i) {
                if (i2 == 0 || (i2 > 1 && str.charAt(length) == '0')) {
                    break;
                }
                return true;
            }
            char charAt = str.charAt(i3);
            if (charAt != '.') {
                if ('0' > charAt || charAt > '9') {
                    break;
                }
                i2++;
                length = i3;
            } else {
                if (i2 == 0 || (i2 > 1 && str.charAt(length) == '0')) {
                    break;
                }
                i2 = 0;
                length = i3;
            }
        }
        return false;
    }

    public static void t(ByteArrayOutputStream byteArrayOutputStream, long j) {
        byte[] bArr = new byte[9];
        int i = 8;
        bArr[8] = (byte) (((int) j) & 127);
        while (j >= 128) {
            j >>= 7;
            i--;
            bArr[i] = (byte) (((int) j) | 128);
        }
        byteArrayOutputStream.write(bArr, i, 9 - i);
    }

    public static void u(ByteArrayOutputStream byteArrayOutputStream, BigInteger bigInteger) {
        int bitLength = (bigInteger.bitLength() + 6) / 7;
        if (bitLength == 0) {
            byteArrayOutputStream.write(0);
            return;
        }
        byte[] bArr = new byte[bitLength];
        int i = bitLength - 1;
        for (int i2 = i; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bigInteger.intValue() | 128);
            bigInteger = bigInteger.shiftRight(7);
        }
        bArr[i] = (byte) (bArr[i] & Byte.MAX_VALUE);
        byteArrayOutputStream.write(bArr, 0, bitLength);
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (this == abstractC0045v) {
            return true;
        }
        if (abstractC0045v instanceof C0047w) {
            return this.a.equals(((C0047w) abstractC0045v).a);
        }
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 13, r());
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, r().length);
    }

    public final synchronized byte[] r() {
        String substring;
        try {
            if (this.b == null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i = 0;
                while (i != -1) {
                    if (i == -1) {
                        substring = null;
                    } else {
                        String str = this.a;
                        int indexOf = str.indexOf(46, i);
                        if (indexOf == -1) {
                            substring = str.substring(i);
                            i = -1;
                        } else {
                            substring = str.substring(i, indexOf);
                            i = indexOf + 1;
                        }
                    }
                    if (substring.length() <= 18) {
                        t(byteArrayOutputStream, Long.parseLong(substring));
                    } else {
                        u(byteArrayOutputStream, new BigInteger(substring));
                    }
                }
                this.b = byteArrayOutputStream.toByteArray();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.b;
    }

    public final String toString() {
        return this.a;
    }
}
