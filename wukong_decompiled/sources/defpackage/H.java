package defpackage;

import android.util.Log;
import com.security.cert.AttestationHelper$AppBinding;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* loaded from: assets/cp.dat */
public abstract class H {
    static {
        "unique_challenge_bytes".getBytes();
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static C0020i b(Map map, String str) {
        if (map != null && map.get(str) != null) {
            try {
                return new C0020i(Integer.parseInt((String) map.get(str)));
            } catch (Exception e) {
                Log.e("AttestationHelper", "enumOrDefault fail: ".concat(str), e);
            }
        }
        return new C0020i(1);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:57:0x01db. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:63:0x020b A[Catch: Exception -> 0x0217, TRY_LEAVE, TryCatch #5 {Exception -> 0x0217, blocks: (B:61:0x0203, B:63:0x020b), top: B:60:0x0203, outer: #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0220 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x022f A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x022f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.security.cert.AttestationHelper$AttestationResult c(java.security.cert.X509Certificate r19) {
        /*
            Method dump skipped, instructions count: 672
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.H.c(java.security.cert.X509Certificate):com.security.cert.AttestationHelper$AttestationResult");
    }

    public static String d(InterfaceC0016g interfaceC0016g) {
        String d;
        if (interfaceC0016g instanceof r) {
            byte[] bArr = ((r) interfaceC0016g).a;
            if (bArr.length == 32) {
                return a(bArr);
            }
            if (bArr.length != 64) {
                return null;
            }
            String str = new String(bArr, StandardCharsets.US_ASCII);
            if (j(str)) {
                return str.toLowerCase();
            }
            return null;
        }
        if (!(interfaceC0016g instanceof AbstractC0051y)) {
            return null;
        }
        Iterator it = ((AbstractC0051y) interfaceC0016g).iterator();
        do {
            G g = (G) it;
            if (!g.hasNext()) {
                return null;
            }
            d = d((InterfaceC0016g) g.next());
        } while (d == null);
        return d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v18, types: [g, y, r0] */
    /* JADX WARN: Type inference failed for: r0v19, types: [y, r0, n] */
    /* JADX WARN: Type inference failed for: r3v1, types: [g, y, r0] */
    /* JADX WARN: Type inference failed for: r8v1, types: [g, z, s0] */
    /* JADX WARN: Type inference failed for: r8v2, types: [y, r0, n] */
    /* JADX WARN: Type inference failed for: r9v1, types: [v, y, r0] */
    /* JADX WARN: Type inference failed for: r9v2, types: [g, z, s0] */
    public static byte[] e(String str, String str2, Map map, Map map2, long j, boolean z) {
        char c;
        int i;
        int i2;
        Object obj;
        char c2;
        int i3;
        C0018h c0018h = new C0018h();
        c0018h.a(new r(str.getBytes("UTF-8")));
        Object obj2 = "manufacturer";
        c0018h.a(new C0028m(j));
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        ?? abstractC0053z = new AbstractC0053z((AbstractC0045v) abstractC0051y);
        abstractC0053z.c = -1;
        ?? abstractC0053z2 = new AbstractC0053z(new r(z ? str2.toLowerCase().getBytes(StandardCharsets.US_ASCII) : g(str2)));
        abstractC0053z2.c = -1;
        C0018h c0018h2 = new C0018h();
        c0018h2.a(abstractC0053z);
        c0018h2.a(abstractC0053z2);
        ?? abstractC0051y2 = new AbstractC0051y(c0018h2);
        abstractC0051y2.c = -1;
        HashSet hashSet = new HashSet();
        if (map != null) {
            for (String str3 : map.keySet()) {
                str3.getClass();
                switch (str3.hashCode()) {
                    case -1969347631:
                        obj = obj2;
                        if (str3.equals(obj)) {
                            c2 = 0;
                            break;
                        } else {
                            c2 = 65535;
                            break;
                        }
                    case -1335157162:
                        if (str3.equals("device")) {
                            obj = obj2;
                            c2 = 1;
                            break;
                        }
                        break;
                    case -309474065:
                        if (str3.equals("product")) {
                            obj = obj2;
                            c2 = 2;
                            break;
                        }
                        break;
                    case 93997959:
                        if (str3.equals("brand")) {
                            obj = obj2;
                            c2 = 3;
                            break;
                        }
                        break;
                    case 104069929:
                        if (str3.equals("model")) {
                            obj = obj2;
                            c2 = 4;
                            break;
                        }
                        break;
                }
                c2 = 65535;
                obj = obj2;
                switch (c2) {
                    case 0:
                        i3 = 716;
                        break;
                    case 1:
                        i3 = 711;
                        break;
                    case 2:
                        i3 = 712;
                        break;
                    case 3:
                        i3 = 710;
                        break;
                    case 4:
                        i3 = 717;
                        break;
                    default:
                        i3 = -1;
                        break;
                }
                if (i3 > 0) {
                    hashSet.add(Integer.valueOf(i3));
                }
                obj2 = obj;
            }
        }
        Object obj3 = obj2;
        C0018h c0018h3 = new C0018h();
        if (map2 != null && map2.get("teeEnforced") != null) {
            try {
                Iterator it = ((AbstractC0051y) AbstractC0045v.o(g((String) map2.get("teeEnforced")))).iterator();
                while (true) {
                    G g = (G) it;
                    if (g.hasNext()) {
                        InterfaceC0016g interfaceC0016g = (InterfaceC0016g) g.next();
                        if (!(interfaceC0016g instanceof X) || ((i2 = ((X) interfaceC0016g).c) != 709 && !hashSet.contains(Integer.valueOf(i2)))) {
                            c0018h3.a(interfaceC0016g);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("AttestationHelper", "teeEnforced rebuild fail", e);
            }
        }
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                String str4 = (String) entry.getKey();
                str4.getClass();
                switch (str4.hashCode()) {
                    case -1969347631:
                        if (str4.equals(obj3)) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1335157162:
                        if (str4.equals("device")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -309474065:
                        if (str4.equals("product")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 93997959:
                        if (str4.equals("brand")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 104069929:
                        if (str4.equals("model")) {
                            c = 4;
                            break;
                        }
                        break;
                }
                c = 65535;
                switch (c) {
                    case 0:
                        i = 716;
                        break;
                    case 1:
                        i = 711;
                        break;
                    case 2:
                        i = 712;
                        break;
                    case 3:
                        i = 710;
                        break;
                    case 4:
                        i = 717;
                        break;
                    default:
                        i = -1;
                        break;
                }
                if (i > 0) {
                    try {
                        c0018h3.a(new X(true, i, (InterfaceC0016g) new r(((String) entry.getValue()).getBytes("UTF-8")), 1));
                    } catch (Exception e2) {
                        Log.e("AttestationHelper", "device prop encode fail: " + ((String) entry.getKey()), e2);
                    }
                }
            }
        }
        ?? abstractC0051y3 = new AbstractC0051y(c0018h3);
        abstractC0051y3.c = -1;
        X x = new X(true, 709, (InterfaceC0016g) new r(abstractC0051y2.g()), 1);
        C0018h c0018h4 = new C0018h();
        c0018h4.a(h(map2, "attestationVersion", 2));
        c0018h4.a(b(map2, "attestationSecurityLevel"));
        c0018h4.a(h(map2, "keymasterVersion", 256));
        c0018h4.a(b(map2, "keymasterSecurityLevel"));
        c0018h4.a(l(map2, "attestationChallenge"));
        c0018h4.a(l(map2, "uniqueId"));
        C0018h c0018h5 = new C0018h();
        if (map2 != null && map2.get("softwareEnforced") != null) {
            try {
                Iterator it2 = ((AbstractC0051y) AbstractC0045v.o(g((String) map2.get("softwareEnforced")))).iterator();
                while (true) {
                    G g2 = (G) it2;
                    if (g2.hasNext()) {
                        InterfaceC0016g interfaceC0016g2 = (InterfaceC0016g) g2.next();
                        if ((interfaceC0016g2 instanceof X) && ((X) interfaceC0016g2).c == 709) {
                        }
                        c0018h5.a(interfaceC0016g2);
                    }
                }
            } catch (Exception e3) {
                Log.e("AttestationHelper", "softwareEnforced rebuild fail", e3);
            }
        }
        c0018h5.a(x);
        ?? abstractC0051y4 = new AbstractC0051y(c0018h5);
        abstractC0051y4.c = -1;
        c0018h4.a(abstractC0051y4);
        c0018h4.a(abstractC0051y3);
        ?? abstractC0051y5 = new AbstractC0051y(c0018h4);
        abstractC0051y5.c = -1;
        return new r(abstractC0051y5.g()).g();
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0041, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean f(defpackage.AbstractC0051y r6) {
        /*
            r0 = 1
            r1 = r0
        L2:
            int r2 = r6.w()
            if (r1 >= r2) goto L44
            g r2 = r6.u(r1)
            boolean r3 = r2 instanceof defpackage.AbstractC0053z
            if (r3 != 0) goto L11
            goto L41
        L11:
            z r2 = (defpackage.AbstractC0053z) r2
            java.util.Iterator r2 = r2.iterator()
        L17:
            r3 = r2
            G r3 = (defpackage.G) r3
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L41
            java.lang.Object r3 = r3.next()
            g r3 = (defpackage.InterfaceC0016g) r3
            boolean r4 = r3 instanceof defpackage.r
            if (r4 == 0) goto L17
            r r3 = (defpackage.r) r3
            byte[] r3 = r3.a
            int r4 = r3.length
            r5 = 64
            if (r4 != r5) goto L17
            java.lang.String r4 = new java.lang.String
            java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.US_ASCII
            r4.<init>(r3, r5)
            boolean r3 = j(r4)
            if (r3 == 0) goto L17
            return r0
        L41:
            int r1 = r1 + 1
            goto L2
        L44:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.H.f(y):boolean");
    }

    public static byte[] g(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) (Character.digit(str.charAt(i + 1), 16) + (Character.digit(str.charAt(i), 16) << 4));
        }
        return bArr;
    }

    public static C0028m h(Map map, String str, int i) {
        if (map != null && map.get(str) != null) {
            try {
                return new C0028m(new BigInteger((String) map.get(str)));
            } catch (Exception e) {
                Log.e("AttestationHelper", "intOrDefault fail: ".concat(str), e);
            }
        }
        return new C0028m(i);
    }

    public static String i(InterfaceC0016g interfaceC0016g) {
        try {
            if (interfaceC0016g instanceof C0028m) {
                return ((C0028m) interfaceC0016g).s().toString();
            }
            if (!(interfaceC0016g instanceof C0020i)) {
                return null;
            }
            C0020i c0020i = (C0020i) interfaceC0016g;
            c0020i.getClass();
            return new BigInteger(c0020i.a).toString();
        } catch (Exception e) {
            Log.e("AttestationHelper", "intStr fail", e);
            return null;
        }
    }

    public static boolean j(String str) {
        int i;
        if (str.length() == 64) {
            for (0; i < str.length(); i + 1) {
                char charAt = str.charAt(i);
                i = ((charAt >= '0' && charAt <= '9') || (charAt >= 'a' && charAt <= 'f') || (charAt >= 'A' && charAt <= 'F')) ? i + 1 : 0;
            }
            return true;
        }
        return false;
    }

    public static String k(InterfaceC0016g interfaceC0016g) {
        try {
            if (interfaceC0016g instanceof r) {
                return a(((r) interfaceC0016g).a);
            }
            return null;
        } catch (Exception e) {
            Log.e("AttestationHelper", "octetHex fail", e);
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [o0, r] */
    /* JADX WARN: Type inference failed for: r1v1, types: [o0, r] */
    public static C0033o0 l(Map map, String str) {
        if (map != null && map.get(str) != null) {
            try {
                return new r(g((String) map.get(str)));
            } catch (Exception e) {
                Log.e("AttestationHelper", "octetOrDefault fail: ".concat(str), e);
            }
        }
        return new r(new byte[0]);
    }

    public static AttestationHelper$AppBinding m(X x, X509Certificate x509Certificate) {
        AbstractC0051y o = o(x);
        if (o == null) {
            throw new RuntimeException("Tag 709 is not a sequence");
        }
        Iterator it = o.iterator();
        String str = null;
        String str2 = null;
        long j = -1;
        while (true) {
            G g = (G) it;
            if (!g.hasNext()) {
                break;
            }
            InterfaceC0016g interfaceC0016g = (InterfaceC0016g) g.next();
            if (interfaceC0016g instanceof AbstractC0053z) {
                Iterator it2 = ((AbstractC0053z) interfaceC0016g).iterator();
                while (true) {
                    G g2 = (G) it2;
                    if (g2.hasNext()) {
                        InterfaceC0016g interfaceC0016g2 = (InterfaceC0016g) g2.next();
                        if (interfaceC0016g2 instanceof AbstractC0051y) {
                            AbstractC0051y abstractC0051y = (AbstractC0051y) interfaceC0016g2;
                            if (abstractC0051y.w() > 0 && (abstractC0051y.u(0) instanceof r)) {
                                String str3 = new String(((r) abstractC0051y.u(0)).a, StandardCharsets.UTF_8);
                                if (str == null) {
                                    str = str3;
                                }
                            }
                            if (abstractC0051y.w() > 1 && (abstractC0051y.u(1) instanceof C0028m)) {
                                j = ((C0028m) abstractC0051y.u(1)).s().longValue();
                            }
                            if (str2 == null) {
                                Iterator it3 = abstractC0051y.iterator();
                                do {
                                    G g3 = (G) it3;
                                    if (g3.hasNext()) {
                                        str2 = d((InterfaceC0016g) g3.next());
                                    }
                                } while (str2 == null);
                            }
                        } else if (str2 == null) {
                            str2 = d(interfaceC0016g2);
                        }
                    }
                }
            }
        }
        if (str == null) {
            throw new RuntimeException("Package name missing in tag 709");
        }
        if (str2 != null) {
            return new AttestationHelper$AppBinding(str, str2, j, x509Certificate);
        }
        throw new RuntimeException("Digest missing in tag 709");
    }

    public static void n(HashMap hashMap, String str, String str2) {
        if (str2 != null) {
            hashMap.put(str, str2);
        }
    }

    public static AbstractC0051y o(X x) {
        AbstractC0045v s = x.s();
        if (s instanceof r) {
            C0026l c0026l = new C0026l(((r) s).a);
            s = c0026l.e();
            c0026l.close();
        }
        if (s instanceof AbstractC0051y) {
            return (AbstractC0051y) s;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
    
        r2 = o((defpackage.X) r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        if (r2 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0045, code lost:
    
        if (f(r2) == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:?, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean p(java.util.Map r2, java.lang.String r3) {
        /*
            if (r2 == 0) goto L51
            java.lang.Object r0 = r2.get(r3)
            if (r0 != 0) goto L9
            goto L51
        L9:
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Exception -> L49
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Exception -> L49
            byte[] r2 = g(r2)     // Catch: java.lang.Exception -> L49
            v r2 = defpackage.AbstractC0045v.o(r2)     // Catch: java.lang.Exception -> L49
            y r2 = (defpackage.AbstractC0051y) r2     // Catch: java.lang.Exception -> L49
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Exception -> L49
        L1d:
            r3 = r2
            G r3 = (defpackage.G) r3     // Catch: java.lang.Exception -> L49
            boolean r0 = r3.hasNext()     // Catch: java.lang.Exception -> L49
            if (r0 == 0) goto L51
            java.lang.Object r3 = r3.next()     // Catch: java.lang.Exception -> L49
            g r3 = (defpackage.InterfaceC0016g) r3     // Catch: java.lang.Exception -> L49
            boolean r0 = r3 instanceof defpackage.X     // Catch: java.lang.Exception -> L49
            if (r0 == 0) goto L1d
            r0 = r3
            X r0 = (defpackage.X) r0     // Catch: java.lang.Exception -> L49
            int r0 = r0.c     // Catch: java.lang.Exception -> L49
            r1 = 709(0x2c5, float:9.94E-43)
            if (r0 != r1) goto L1d
            X r3 = (defpackage.X) r3     // Catch: java.lang.Exception -> L49
            y r2 = o(r3)     // Catch: java.lang.Exception -> L49
            if (r2 == 0) goto L51
            boolean r2 = f(r2)     // Catch: java.lang.Exception -> L49
            if (r2 == 0) goto L51
            r2 = 1
            return r2
        L49:
            r2 = move-exception
            java.lang.String r3 = "AttestationHelper"
            java.lang.String r0 = "tag 709 digest format detection fail"
            android.util.Log.e(r3, r0, r2)
        L51:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.H.p(java.util.Map, java.lang.String):boolean");
    }
}
