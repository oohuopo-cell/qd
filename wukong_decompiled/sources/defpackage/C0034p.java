package defpackage;

import java.util.Arrays;

/* renamed from: p, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0034p {
    public final int a;
    public final byte[] b;

    public C0034p(byte[] bArr) {
        this.a = D.f(bArr);
        this.b = bArr;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0034p)) {
            return false;
        }
        return Arrays.equals(this.b, ((C0034p) obj).b);
    }

    public final int hashCode() {
        return this.a;
    }
}
