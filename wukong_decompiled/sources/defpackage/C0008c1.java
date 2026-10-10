package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* renamed from: c1, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0008c1 implements Enumeration {
    public C0026l a;
    public AbstractC0045v b;

    public final AbstractC0045v a() {
        try {
            return this.a.e();
        } catch (IOException e) {
            throw new C0043u("malformed ASN.1: " + e, e);
        }
    }

    @Override // java.util.Enumeration
    public final boolean hasMoreElements() {
        return this.b != null;
    }

    @Override // java.util.Enumeration
    public final Object nextElement() {
        AbstractC0045v abstractC0045v = this.b;
        if (abstractC0045v == null) {
            throw new NoSuchElementException();
        }
        this.b = a();
        return abstractC0045v;
    }
}
