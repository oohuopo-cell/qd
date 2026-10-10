package defpackage;

/* loaded from: assets/cp.dat */
public abstract class u1 {
    public static final /* synthetic */ int a = 0;

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object, java.security.PrivilegedAction] */
    static {
        try {
            try {
            } catch (Exception unused) {
                String.format("%n", new Object[0]);
            }
        } catch (Exception unused2) {
        }
    }

    public static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return new String(cArr);
    }

    public static byte[] b(String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i != length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }
}
