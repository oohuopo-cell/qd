package defpackage;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: q, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0036q extends AbstractC0045v {
    public static final ConcurrentHashMap c;
    public final String a;
    public byte[] b;

    static {
        new C0003b(C0036q.class, 4);
        c = new ConcurrentHashMap();
    }

    public C0036q(String str) {
        char charAt;
        boolean z = false;
        if (str.length() >= 3 && str.charAt(1) == '.' && (charAt = str.charAt(0)) >= '0' && charAt <= '2') {
            z = C0047w.s(str, 2);
        }
        if (z) {
            this.a = str;
            return;
        }
        throw new IllegalArgumentException("string " + str + " not an OID");
    }

    public C0036q(C0036q c0036q, String str) {
        if (!C0047w.s(str, 0)) {
            throw new IllegalArgumentException("string " + str + " not a valid OID branch");
        }
        this.a = c0036q.a + "." + str;
    }

    public C0036q(byte[] bArr, boolean z) {
        byte[] bArr2 = bArr;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = true;
        long j = 0;
        BigInteger bigInteger = null;
        for (int i = 0; i != bArr2.length; i++) {
            byte b = bArr2[i];
            if (j <= 72057594037927808L) {
                long j2 = j + (b & Byte.MAX_VALUE);
                if ((b & 128) == 0) {
                    if (z2) {
                        if (j2 < 40) {
                            stringBuffer.append('0');
                        } else if (j2 < 80) {
                            stringBuffer.append('1');
                            j2 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j2 -= 80;
                        }
                        z2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                }
            } else {
                BigInteger or = (bigInteger == null ? BigInteger.valueOf(j) : bigInteger).or(BigInteger.valueOf(b & Byte.MAX_VALUE));
                if ((b & 128) == 0) {
                    if (z2) {
                        stringBuffer.append('2');
                        or = or.subtract(BigInteger.valueOf(80L));
                        z2 = false;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(or);
                    j = 0;
                    bigInteger = null;
                } else {
                    bigInteger = or.shiftLeft(7);
                }
            }
        }
        this.a = stringBuffer.toString();
        this.b = z ? D.d(bArr2) : bArr2;
    }

    public static C0036q t(InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g == null || (interfaceC0016g instanceof C0036q)) {
            return (C0036q) interfaceC0016g;
        }
        AbstractC0045v e = interfaceC0016g.e();
        if (e instanceof C0036q) {
            return (C0036q) e;
        }
        throw new IllegalArgumentException("illegal object in getInstance: ".concat(interfaceC0016g.getClass().getName()));
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (abstractC0045v == this) {
            return true;
        }
        if (!(abstractC0045v instanceof C0036q)) {
            return false;
        }
        return this.a.equals(((C0036q) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 6, s());
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return false;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, s().length);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x005b  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0079 -> B:10:0x0044). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void r(java.io.ByteArrayOutputStream r12) {
        /*
            r11 = this;
            java.lang.String r0 = r11.a
            r1 = 46
            r2 = 0
            int r3 = r0.indexOf(r1, r2)
            r4 = -1
            if (r3 != r4) goto L12
            java.lang.String r2 = r0.substring(r2)
            r3 = r4
            goto L18
        L12:
            java.lang.String r2 = r0.substring(r2, r3)
            int r3 = r3 + 1
        L18:
            int r2 = java.lang.Integer.parseInt(r2)
            int r2 = r2 * 40
            r5 = 0
            if (r3 != r4) goto L24
            r6 = r3
            r3 = r5
            goto L36
        L24:
            int r6 = r0.indexOf(r1, r3)
            if (r6 != r4) goto L30
            java.lang.String r3 = r0.substring(r3)
            r6 = r4
            goto L36
        L30:
            java.lang.String r3 = r0.substring(r3, r6)
            int r6 = r6 + 1
        L36:
            int r7 = r3.length()
            r8 = 18
            if (r7 > r8) goto L48
            long r9 = (long) r2
            long r2 = java.lang.Long.parseLong(r3)
            long r2 = r2 + r9
        L44:
            defpackage.C0047w.t(r12, r2)
            goto L59
        L48:
            java.math.BigInteger r7 = new java.math.BigInteger
            r7.<init>(r3)
            long r2 = (long) r2
            java.math.BigInteger r2 = java.math.BigInteger.valueOf(r2)
            java.math.BigInteger r2 = r7.add(r2)
            defpackage.C0047w.u(r12, r2)
        L59:
            if (r6 == r4) goto L87
            if (r6 != r4) goto L5f
            r2 = r5
            goto L73
        L5f:
            int r2 = r0.indexOf(r1, r6)
            if (r2 != r4) goto L6b
            java.lang.String r2 = r0.substring(r6)
            r6 = r4
            goto L73
        L6b:
            java.lang.String r3 = r0.substring(r6, r2)
            int r2 = r2 + 1
            r6 = r2
            r2 = r3
        L73:
            int r3 = r2.length()
            if (r3 > r8) goto L7e
            long r2 = java.lang.Long.parseLong(r2)
            goto L44
        L7e:
            java.math.BigInteger r3 = new java.math.BigInteger
            r3.<init>(r2)
            defpackage.C0047w.u(r12, r3)
            goto L59
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.C0036q.r(java.io.ByteArrayOutputStream):void");
    }

    public final synchronized byte[] s() {
        try {
            if (this.b == null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                r(byteArrayOutputStream);
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

    public final C0036q u() {
        C0034p c0034p = new C0034p(s());
        ConcurrentHashMap concurrentHashMap = c;
        C0036q c0036q = (C0036q) concurrentHashMap.get(c0034p);
        if (c0036q != null) {
            return c0036q;
        }
        C0036q c0036q2 = (C0036q) concurrentHashMap.putIfAbsent(c0034p, this);
        return c0036q2 == null ? this : c0036q2;
    }
}
