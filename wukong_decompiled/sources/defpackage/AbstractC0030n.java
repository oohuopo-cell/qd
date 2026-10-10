package defpackage;

import java.io.ByteArrayOutputStream;

/* renamed from: n, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public abstract class AbstractC0030n implements InterfaceC0016g {
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InterfaceC0016g) {
            return e().n(((InterfaceC0016g) obj).e());
        }
        return false;
    }

    public final byte[] g() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AbstractC0045v e = e();
        e.getClass();
        e.j(new C0041t(byteArrayOutputStream), true);
        return byteArrayOutputStream.toByteArray();
    }

    public final byte[] h() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        e().l(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public int hashCode() {
        return e().hashCode();
    }
}
