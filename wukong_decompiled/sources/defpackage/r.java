package defpackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: assets/cp.dat */
public abstract class r extends AbstractC0045v implements InterfaceC0039s {
    public static final C0003b b = new C0003b(r.class, 5);
    public static final byte[] c = new byte[0];
    public final byte[] a;

    public r(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.a = bArr;
    }

    public static r r(Object obj) {
        if (obj == null || (obj instanceof r)) {
            return (r) obj;
        }
        if (obj instanceof InterfaceC0016g) {
            AbstractC0045v e = ((InterfaceC0016g) obj).e();
            if (e instanceof r) {
                return (r) e;
            }
        } else if (obj instanceof byte[]) {
            try {
                C0003b c0003b = b;
                AbstractC0045v o = AbstractC0045v.o((byte[]) obj);
                c0003b.a(o);
                return (r) o;
            } catch (IOException e2) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e2.getMessage());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: ".concat(obj.getClass().getName()));
    }

    @Override // defpackage.W0
    public final AbstractC0045v b() {
        return this;
    }

    @Override // defpackage.InterfaceC0039s
    public final InputStream c() {
        return new ByteArrayInputStream(this.a);
    }

    @Override // defpackage.AbstractC0045v, defpackage.AbstractC0030n
    public final int hashCode() {
        return D.f(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean i(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof r)) {
            return false;
        }
        return Arrays.equals(this.a, ((r) abstractC0045v).a);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v p() {
        return new r(this.a);
    }

    @Override // defpackage.AbstractC0045v
    public AbstractC0045v q() {
        return new r(this.a);
    }

    public final String toString() {
        U0 u0 = T0.a;
        byte[] bArr = this.a;
        return "#".concat(u1.a(T0.a(bArr, bArr.length)));
    }
}
