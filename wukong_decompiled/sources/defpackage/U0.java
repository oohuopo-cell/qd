package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: assets/cp.dat */
public final class U0 {
    public Object a;
    public Object b;

    /* JADX WARN: Type inference failed for: r0v15, types: [y, r0, n] */
    /* JADX WARN: Type inference failed for: r2v1, types: [o0, r] */
    /* JADX WARN: Type inference failed for: r2v4, types: [o0, r] */
    public void a(C0036q c0036q, boolean z, byte[] bArr) {
        Q0 q0 = (Q0) this.b;
        Hashtable hashtable = q0.a;
        if (!hashtable.containsKey(c0036q)) {
            q0.b.addElement(c0036q);
            hashtable.put(c0036q, new O0(c0036q, z, new r(bArr)));
            return;
        }
        if (!Q0.c.contains(c0036q)) {
            throw new IllegalArgumentException("extension " + c0036q + " already added");
        }
        AbstractC0051y t = AbstractC0051y.t(r.r(((O0) hashtable.get(c0036q)).c).a);
        AbstractC0051y t2 = AbstractC0051y.t(bArr);
        C0018h c0018h = new C0018h(t2.w() + t.w());
        Enumeration v = t.v();
        while (v.hasMoreElements()) {
            c0018h.a((InterfaceC0016g) v.nextElement());
        }
        Enumeration v2 = t2.v();
        while (v2.hasMoreElements()) {
            c0018h.a((InterfaceC0016g) v2.nextElement());
        }
        try {
            ?? abstractC0051y = new AbstractC0051y(c0018h);
            abstractC0051y.c = -1;
            hashtable.put(c0036q, new O0(c0036q, z, new r(abstractC0051y.g())));
        } catch (IOException e) {
            throw new C0043u(e.getMessage(), e);
        }
    }

    public byte[] b(String str, int i) {
        if (i >= 0) {
            int i2 = 1;
            if (1 <= str.length() - i) {
                if ((i & 1) != 0) {
                    throw new IOException("a hexadecimal encoding must have an even number of characters");
                }
                int i3 = i >>> 1;
                byte[] bArr = new byte[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    char charAt = str.charAt(i2);
                    byte[] bArr2 = (byte[]) this.b;
                    i2 += 2;
                    int i5 = bArr2[str.charAt(i2 + 1)] | (bArr2[charAt] << 4);
                    if (i5 < 0) {
                        throw new IOException("invalid characters encountered in Hex string");
                    }
                    bArr[i4] = (byte) i5;
                }
                return bArr;
            }
        }
        throw new IndexOutOfBoundsException("invalid offset and/or length specified");
    }
}
