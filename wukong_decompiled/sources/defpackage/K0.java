package defpackage;

import java.util.HashMap;

/* loaded from: assets/cp.dat */
public final class K0 {
    public static final HashMap a;
    public static final HashMap b;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        HashMap hashMap2 = new HashMap();
        b = hashMap2;
        hashMap.put(l1.g, "RSASSA-PSS");
        hashMap.put(N0.a, "ED25519");
        hashMap.put(N0.b, "ED448");
        hashMap.put(new C0036q("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        hashMap.put(l1.k, "SHA224WITHRSA");
        hashMap.put(l1.h, "SHA256WITHRSA");
        hashMap.put(l1.i, "SHA384WITHRSA");
        hashMap.put(l1.j, "SHA512WITHRSA");
        hashMap.put(InterfaceC0001a0.a, "SHAKE128WITHRSAPSS");
        hashMap.put(InterfaceC0001a0.b, "SHAKE256WITHRSAPSS");
        hashMap.put(InterfaceC0013e0.c, "GOST3411WITHGOST3410");
        hashMap.put(InterfaceC0013e0.d, "GOST3411WITHECGOST3410");
        hashMap.put(q1.c, "GOST3411-2012-256WITHECGOST3410-2012-256");
        hashMap.put(q1.d, "GOST3411-2012-512WITHECGOST3410-2012-512");
        hashMap.put(Z.a, "SHA1WITHPLAIN-ECDSA");
        hashMap.put(Z.b, "SHA224WITHPLAIN-ECDSA");
        hashMap.put(Z.c, "SHA256WITHPLAIN-ECDSA");
        hashMap.put(Z.d, "SHA384WITHPLAIN-ECDSA");
        hashMap.put(Z.e, "SHA512WITHPLAIN-ECDSA");
        hashMap.put(Z.g, "SHA3-224WITHPLAIN-ECDSA");
        hashMap.put(Z.h, "SHA3-256WITHPLAIN-ECDSA");
        hashMap.put(Z.i, "SHA3-384WITHPLAIN-ECDSA");
        hashMap.put(Z.j, "SHA3-512WITHPLAIN-ECDSA");
        hashMap.put(Z.f, "RIPEMD160WITHPLAIN-ECDSA");
        hashMap.put(M0.a, "SHA1WITHCVC-ECDSA");
        hashMap.put(M0.b, "SHA224WITHCVC-ECDSA");
        hashMap.put(M0.c, "SHA256WITHCVC-ECDSA");
        hashMap.put(M0.d, "SHA384WITHCVC-ECDSA");
        hashMap.put(M0.e, "SHA512WITHCVC-ECDSA");
        hashMap.put(Y0.a, "XMSS");
        hashMap.put(Y0.b, "XMSSMT");
        hashMap.put(x1.e, "RIPEMD128WITHRSA");
        hashMap.put(x1.d, "RIPEMD160WITHRSA");
        hashMap.put(x1.f, "RIPEMD256WITHRSA");
        hashMap.put(new C0036q("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        hashMap.put(new C0036q("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        hashMap.put(new C0036q("1.2.840.10040.4.3"), "SHA1WITHDSA");
        hashMap.put(D1.a, "SHA1WITHECDSA");
        hashMap.put(D1.b, "SHA224WITHECDSA");
        hashMap.put(D1.c, "SHA256WITHECDSA");
        hashMap.put(D1.d, "SHA384WITHECDSA");
        hashMap.put(D1.e, "SHA512WITHECDSA");
        hashMap.put(InterfaceC0001a0.c, "SHAKE128WITHECDSA");
        hashMap.put(InterfaceC0001a0.d, "SHAKE256WITHECDSA");
        hashMap.put(i1.c, "SHA1WITHRSA");
        hashMap.put(i1.b, "SHA1WITHDSA");
        hashMap.put(g1.v, "SHA224WITHDSA");
        hashMap.put(g1.w, "SHA256WITHDSA");
        hashMap2.put(i1.a, "SHA1");
        hashMap2.put(g1.d, "SHA224");
        hashMap2.put(g1.a, "SHA256");
        hashMap2.put(g1.b, "SHA384");
        hashMap2.put(g1.c, "SHA512");
        hashMap2.put(g1.g, "SHA3-224");
        hashMap2.put(g1.h, "SHA3-256");
        hashMap2.put(g1.i, "SHA3-384");
        hashMap2.put(g1.j, "SHA3-512");
        hashMap2.put(x1.b, "RIPEMD128");
        hashMap2.put(x1.a, "RIPEMD160");
        hashMap2.put(x1.c, "RIPEMD256");
    }

    public static String a(C0036q c0036q) {
        String str = (String) b.get(c0036q);
        return str != null ? str : c0036q.a;
    }
}
