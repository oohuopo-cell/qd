package defpackage;

/* loaded from: assets/cp.dat */
public final class G0 extends Y {
    public final boolean d;

    public G0(int i, int i2, boolean z, A a) {
        super(i, i2, a);
        this.d = z;
    }

    @Override // defpackage.Y, defpackage.W0
    public final AbstractC0045v b() {
        A a = this.c;
        int i = this.b;
        boolean z = this.d;
        int i2 = this.a;
        if (z) {
            return X.r(i2, i, a.c());
        }
        X x = new X(4, i2, i, new r(((L0) a.a).b()), 2);
        return i2 != 64 ? x : new AbstractC0000a(x);
    }
}
