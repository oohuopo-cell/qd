package defpackage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* renamed from: d1, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0011d1 extends AbstractC0051y {
    public byte[] c;

    public final synchronized void A() {
        C0018h c0018h;
        if (this.c != null) {
            C0026l c0026l = new C0026l(this.c, 0);
            try {
                AbstractC0045v e = c0026l.e();
                if (e == null) {
                    c0018h = new C0018h(0);
                } else {
                    C0018h c0018h2 = new C0018h();
                    do {
                        c0018h2.a(e);
                        e = c0026l.e();
                    } while (e != null);
                    c0018h = c0018h2;
                }
                c0026l.close();
                this.a = c0018h.c();
                this.c = null;
            } catch (IOException e2) {
                throw new C0043u("malformed ASN.1: " + e2, e2);
            }
        }
    }

    @Override // defpackage.AbstractC0051y, defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        A();
        return super.hashCode();
    }

    @Override // defpackage.AbstractC0051y, java.lang.Iterable
    public final Iterator iterator() {
        A();
        return super.iterator();
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        if (bArr != null) {
            c0041t.j(z, 48, bArr);
        } else {
            super.q().j(c0041t, z);
        }
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        return bArr != null ? C0041t.d(z, bArr.length) : super.q().m(z);
    }

    @Override // defpackage.AbstractC0051y, defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        A();
        return super.p();
    }

    @Override // defpackage.AbstractC0051y, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        A();
        return super.q();
    }

    @Override // defpackage.AbstractC0051y
    public final InterfaceC0016g u(int i) {
        A();
        return this.a[i];
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [java.util.Enumeration, java.lang.Object, c1] */
    @Override // defpackage.AbstractC0051y
    public final Enumeration v() {
        byte[] bArr;
        synchronized (this) {
            bArr = this.c;
        }
        if (bArr == null) {
            return new C0049x(this);
        }
        ?? obj = new Object();
        obj.a = new C0026l(bArr, 0);
        obj.b = obj.a();
        return obj;
    }

    @Override // defpackage.AbstractC0051y
    public final int w() {
        A();
        return this.a.length;
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0006c x() {
        return ((AbstractC0051y) q()).x();
    }

    @Override // defpackage.AbstractC0051y
    public final r y() {
        return ((AbstractC0051y) q()).y();
    }

    @Override // defpackage.AbstractC0051y
    public final AbstractC0053z z() {
        return ((AbstractC0051y) q()).z();
    }
}
