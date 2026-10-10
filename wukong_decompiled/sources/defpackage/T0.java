package defpackage;

import java.io.ByteArrayOutputStream;

/* loaded from: assets/cp.dat */
public abstract class T0 {
    public static final U0 a;

    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object, U0] */
    static {
        byte[] bArr;
        ?? obj = new Object();
        obj.a = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        obj.b = new byte[128];
        int i = 0;
        int i2 = 0;
        while (true) {
            bArr = (byte[]) obj.b;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = -1;
            i2++;
        }
        while (true) {
            byte[] bArr2 = (byte[]) obj.a;
            if (i >= bArr2.length) {
                bArr[65] = bArr[97];
                bArr[66] = bArr[98];
                bArr[67] = bArr[99];
                bArr[68] = bArr[100];
                bArr[69] = bArr[101];
                bArr[70] = bArr[102];
                a = obj;
                return;
            }
            bArr[bArr2[i]] = (byte) i;
            i++;
        }
    }

    public static byte[] a(byte[] bArr, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            U0 u0 = a;
            u0.getClass();
            if (i >= 0) {
                byte[] bArr2 = new byte[72];
                int i2 = 0;
                while (i > 0) {
                    int min = Math.min(36, i);
                    int i3 = i2 + min;
                    int i4 = 0;
                    while (i2 < i3) {
                        int i5 = i2 + 1;
                        byte b = bArr[i2];
                        int i6 = i4 + 1;
                        byte[] bArr3 = (byte[]) u0.a;
                        bArr2[i4] = bArr3[(b & 255) >>> 4];
                        i4 += 2;
                        bArr2[i6] = bArr3[b & 15];
                        i2 = i5;
                    }
                    byteArrayOutputStream.write(bArr2, 0, i4);
                    i -= min;
                    i2 = i3;
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            C0043u c0043u = new C0043u("exception encoding Hex string: " + e.getMessage(), 2);
            c0043u.b = e;
            throw c0043u;
        }
    }
}
