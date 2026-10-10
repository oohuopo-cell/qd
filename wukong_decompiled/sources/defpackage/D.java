package defpackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: assets/cp.dat */
public abstract class D {
    public static void a(StringBuffer stringBuffer, I i, Hashtable hashtable) {
        String str = (String) hashtable.get(i.a);
        if (str == null) {
            str = i.a.a;
        }
        stringBuffer.append(str);
        stringBuffer.append('=');
        stringBuffer.append(j(i.b));
    }

    public static boolean b(B1 b1, B1 b12) {
        o1[] o1VarArr = (o1[]) b1.d.clone();
        o1[] o1VarArr2 = (o1[]) b12.d.clone();
        if (o1VarArr.length == o1VarArr2.length) {
            boolean z = (o1VarArr[0].i() == null || o1VarArr2[0].i() == null) ? false : !o1VarArr[0].i().a.n(o1VarArr2[0].i().a);
            for (int i = 0; i != o1VarArr.length; i++) {
                o1 o1Var = o1VarArr[i];
                if (z) {
                    for (int length = o1VarArr2.length - 1; length >= 0; length--) {
                        o1 o1Var2 = o1VarArr2[length];
                        if (o1Var2 != null && g(o1Var, o1Var2)) {
                            o1VarArr2[length] = null;
                        }
                    }
                } else {
                    for (int i2 = 0; i2 != o1VarArr2.length; i2++) {
                        o1 o1Var3 = o1VarArr2[i2];
                        if (o1Var3 != null && g(o1Var, o1Var3)) {
                            o1VarArr2[i2] = null;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ba, code lost:
    
        if (r7 >= r2) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String c(defpackage.InterfaceC0016g r9) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.D.c(g):java.lang.String");
    }

    public static byte[] d(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return (byte[]) bArr.clone();
    }

    public static String e(int i, int i2) {
        StringBuilder sb = i != 64 ? i != 128 ? i != 192 ? new StringBuilder("[UNIVERSAL ") : new StringBuilder("[PRIVATE ") : new StringBuilder("[CONTEXT ") : new StringBuilder("[APPLICATION ");
        sb.append(i2);
        sb.append("]");
        return sb.toString();
    }

    public static int f(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int length = bArr.length;
        int i = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return i;
            }
            i = (i * 257) ^ bArr[length];
        }
    }

    public static boolean g(o1 o1Var, o1 o1Var2) {
        int i;
        if (o1Var.a.a.length == o1Var2.a.a.length) {
            I[] j = o1Var.j();
            I[] j2 = o1Var2.j();
            if (j.length == j2.length) {
                for (0; i != j.length; i + 1) {
                    I i2 = j[i];
                    I i3 = j2[i];
                    i = (i2 == i3 || (i2 != null && i3 != null && i2.a.n(i3.a) && c(i2.b).equals(c(i3.b)))) ? i + 1 : 0;
                }
                return true;
            }
        }
        return false;
    }

    public static int h(InputStream inputStream, byte[] bArr, int i) {
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read < 0) {
                break;
            }
            i2 += read;
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [java.lang.Throwable, Z0, java.security.cert.CertificateException] */
    /* JADX WARN: Type inference failed for: r0v14, types: [a1, java.lang.Throwable, java.security.cert.CertificateParsingException] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable, j1, java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r15v14, types: [y, java.lang.Object, r0] */
    /* JADX WARN: Type inference failed for: r15v21, types: [E, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.Object, Q0] */
    /* JADX WARN: Type inference failed for: r1v8, types: [k1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.io.OutputStream, s1] */
    /* JADX WARN: Type inference failed for: r4v9, types: [P0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.Object, U0] */
    /* JADX WARN: Type inference failed for: r8v1, types: [A1, java.lang.Object] */
    public static X509Certificate i(X509Certificate x509Certificate, byte[] bArr, PrivateKey privateKey, String str) {
        boolean z;
        E e;
        C0004b0 i = C0004b0.i(x509Certificate.getEncoded());
        w1 w1Var = i.b;
        P0 p0 = w1Var.l;
        B1 i2 = B1.i(w1Var.e);
        v1 i3 = v1.i(x509Certificate.getPublicKey().getEncoded());
        BigInteger s = i.b.c.s();
        Date notBefore = x509Certificate.getNotBefore();
        Date notAfter = x509Certificate.getNotAfter();
        B1 i4 = B1.i(i.b.h);
        y1 y1Var = new y1(notBefore);
        y1 y1Var2 = new y1(notAfter);
        ?? obj = new Object();
        ?? obj2 = new Object();
        obj2.a = new X(true, 0, (InterfaceC0016g) new C0028m(2L), 1);
        obj.a = obj2;
        obj2.b = new C0028m(s);
        obj2.d = i2;
        obj2.e = y1Var;
        obj2.f = y1Var2;
        obj2.g = i4;
        obj2.h = i3;
        ?? obj3 = new Object();
        obj3.a = new Hashtable();
        obj3.b = new Vector();
        obj.b = obj3;
        byte[] bArr2 = bArr != null ? r.r(bArr).a : bArr;
        if (p0 != null) {
            Vector vector = p0.b;
            int size = vector.size();
            C0036q[] c0036qArr = new C0036q[size];
            for (int i5 = 0; i5 != size; i5++) {
                c0036qArr[i5] = (C0036q) vector.elementAt(i5);
            }
            z = false;
            for (int i6 = 0; i6 < size; i6++) {
                C0036q c0036q = c0036qArr[i6];
                O0 o0 = (O0) p0.a.get(c0036q);
                if (c0036q.a.equals("1.3.6.1.4.1.11129.2.1.17")) {
                    obj.a(new C0036q("1.3.6.1.4.1.11129.2.1.17"), false, bArr2);
                    z = true;
                } else {
                    Q0 q0 = (Q0) obj.b;
                    Hashtable hashtable = q0.a;
                    boolean containsKey = hashtable.containsKey(o0.a);
                    C0036q c0036q2 = o0.a;
                    if (containsKey) {
                        throw new IllegalArgumentException("extension " + c0036q2 + " already added");
                    }
                    q0.b.addElement(c0036q2);
                    hashtable.put(c0036q2, o0);
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            obj.a(new C0036q("1.3.6.1.4.1.11129.2.1.17"), false, bArr2);
        }
        String sigAlgName = str != null ? str : x509Certificate.getSigAlgName();
        ?? obj4 = new Object();
        int i7 = u1.a;
        char[] charArray = sigAlgName.toCharArray();
        boolean z2 = false;
        for (int i8 = 0; i8 != charArray.length; i8++) {
            char c = charArray[i8];
            if ('a' <= c && 'z' >= c) {
                charArray[i8] = (char) (c - ' ');
                z2 = true;
            }
        }
        if (z2) {
            sigAlgName = new String(charArray);
        }
        C0036q c0036q3 = (C0036q) J0.a.get(sigAlgName);
        if (c0036q3 == null) {
            throw new IllegalArgumentException("Unknown signature type requested: ".concat(sigAlgName));
        }
        if (J0.b.contains(c0036q3)) {
            ?? obj5 = new Object();
            obj5.a = c0036q3;
            e = obj5;
        } else {
            HashMap hashMap = J0.c;
            e = hashMap.containsKey(sigAlgName) ? new E(c0036q3, (InterfaceC0016g) hashMap.get(sigAlgName)) : new E(c0036q3, C0029m0.a);
        }
        try {
            Signature a = obj4.a(e);
            a.initSign(privateKey);
            ?? outputStream = new OutputStream();
            outputStream.a = a;
            A1 a1 = (A1) obj.a;
            a1.c = e;
            Q0 q02 = (Q0) obj.b;
            if (!q02.b.isEmpty()) {
                Vector vector2 = q02.b;
                int size2 = vector2.size();
                O0[] o0Arr = new O0[size2];
                for (int i9 = 0; i9 != vector2.size(); i9++) {
                    o0Arr[i9] = (O0) q02.a.get(vector2.elementAt(i9));
                }
                ?? obj6 = new Object();
                obj6.a = new Hashtable();
                obj6.b = new Vector();
                for (int i10 = 0; i10 != size2; i10++) {
                    O0 o02 = o0Arr[i10];
                    obj6.b.addElement(o02.a);
                    obj6.a.put(o02.a, o02);
                }
                a1.i = obj6;
                O0 o03 = (O0) obj6.a.get(O0.e);
                if (o03 != null && o03.b) {
                    a1.j = true;
                }
            }
            try {
                w1 a2 = a1.a();
                a2.e().l(outputStream);
                outputStream.close();
                try {
                    byte[] sign = a.sign();
                    C0018h c0018h = new C0018h();
                    c0018h.a(a2);
                    c0018h.a(e);
                    c0018h.a(new AbstractC0006c(sign, 0));
                    ?? abstractC0051y = new AbstractC0051y(c0018h);
                    abstractC0051y.c = -1;
                    C0004b0 i11 = C0004b0.i(abstractC0051y);
                    P0 p02 = i11.b.l;
                    try {
                        return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(i11.g()));
                    } catch (IOException e2) {
                        ?? certificateParsingException = new CertificateParsingException("exception parsing certificate: " + e2.getMessage());
                        certificateParsingException.a = e2;
                        throw certificateParsingException;
                    } catch (NoSuchProviderException e3) {
                        ?? certificateException = new CertificateException("cannot find required provider:" + e3.getMessage());
                        certificateException.a = e3;
                        throw certificateException;
                    }
                } catch (SignatureException e4) {
                    throw new r1("exception obtaining signature: " + e4.getMessage(), e4);
                }
            } catch (IOException unused) {
                throw new IllegalArgumentException("cannot produce certificate signature");
            }
        } catch (GeneralSecurityException e5) {
            ?? exc = new Exception("cannot create signer: " + e5.getMessage());
            exc.a = e5;
            throw exc;
        }
    }

    public static String j(InterfaceC0016g interfaceC0016g) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!(interfaceC0016g instanceof B) || (interfaceC0016g instanceof C0048w0)) {
            try {
                stringBuffer.append('#');
                byte[] h = interfaceC0016g.e().h();
                U0 u0 = T0.a;
                stringBuffer.append(u1.a(T0.a(h, h.length)));
            } catch (IOException unused) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        } else {
            String a = ((B) interfaceC0016g).a();
            if (a.length() > 0 && a.charAt(0) == '#') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(a);
        }
        int length = stringBuffer.length();
        int i = (stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#') ? 2 : 0;
        while (i != length) {
            char charAt = stringBuffer.charAt(i);
            if (charAt != '\"' && charAt != '\\' && charAt != '+' && charAt != ',') {
                switch (charAt) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        break;
                    default:
                        i++;
                        continue;
                }
            }
            stringBuffer.insert(i, "\\");
            i += 2;
            length++;
        }
        if (stringBuffer.length() > 0) {
            for (int i2 = 0; stringBuffer.length() > i2 && stringBuffer.charAt(i2) == ' '; i2 += 2) {
                stringBuffer.insert(i2, "\\");
            }
        }
        for (int length2 = stringBuffer.length() - 1; length2 >= 0 && stringBuffer.charAt(length2) == ' '; length2--) {
            stringBuffer.insert(length2, '\\');
        }
        return stringBuffer.toString();
    }
}
