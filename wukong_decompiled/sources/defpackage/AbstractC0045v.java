package defpackage;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: v, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0045v extends AbstractC0030n {
    public static AbstractC0045v o(byte[] bArr) {
        C0026l c0026l = new C0026l(bArr);
        try {
            AbstractC0045v e = c0026l.e();
            if (c0026l.available() == 0) {
                return e;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        return this;
    }

    @Override // defpackage.AbstractC0030n
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC0016g) && i(((InterfaceC0016g) obj).e());
    }

    @Override // defpackage.AbstractC0030n
    public abstract int hashCode();

    public abstract boolean i(AbstractC0045v abstractC0045v);

    public abstract void j(C0041t c0041t, boolean z);

    public abstract boolean k();

    public final void l(OutputStream outputStream) {
        new C0041t(outputStream).n(this);
    }

    public abstract int m(boolean z);

    public final boolean n(AbstractC0045v abstractC0045v) {
        return this == abstractC0045v || i(abstractC0045v);
    }

    public AbstractC0045v p() {
        return this;
    }

    public AbstractC0045v q() {
        return this;
    }
}
