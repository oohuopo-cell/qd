package defpackage;

/* loaded from: assets/cp.dat */
public final class S extends r {
    public final int d;
    public final r[] e;

    public S(byte[] bArr, r[] rVarArr) {
        super(bArr);
        this.e = rVarArr;
        this.d = 1000;
    }

    public static byte[] s(r[] rVarArr) {
        int length = rVarArr.length;
        if (length == 0) {
            return r.c;
        }
        if (length == 1) {
            return rVarArr[0].a;
        }
        int i = 0;
        for (r rVar : rVarArr) {
            i += rVar.a.length;
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (r rVar2 : rVarArr) {
            byte[] bArr2 = rVar2.a;
            System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
            i2 += bArr2.length;
        }
        return bArr;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        if (!k()) {
            byte[] bArr = this.a;
            int length = bArr.length;
            c0041t.m(z, 4);
            c0041t.h(length);
            c0041t.g(bArr, 0, length);
            return;
        }
        c0041t.m(z, 36);
        c0041t.f(128);
        r[] rVarArr = this.e;
        if (rVarArr == null) {
            int i = 0;
            while (true) {
                byte[] bArr2 = this.a;
                if (i >= bArr2.length) {
                    break;
                }
                int min = Math.min(bArr2.length - i, this.d);
                byte[] bArr3 = this.a;
                c0041t.m(true, 4);
                c0041t.h(min);
                c0041t.g(bArr3, i, min);
                i += min;
            }
        } else {
            c0041t.o(rVarArr);
        }
        c0041t.f(0);
        c0041t.f(0);
    }

    @Override // defpackage.AbstractC0045v
    public final boolean k() {
        return this.e != null || this.a.length > this.d;
    }

    @Override // defpackage.AbstractC0045v
    public final int m(boolean z) {
        boolean k = k();
        byte[] bArr = this.a;
        if (!k) {
            return C0041t.d(z, bArr.length);
        }
        int i = z ? 4 : 3;
        r[] rVarArr = this.e;
        if (rVarArr != null) {
            for (r rVar : rVarArr) {
                i += rVar.m(true);
            }
            return i;
        }
        int length = bArr.length;
        int i2 = this.d;
        int i3 = length / i2;
        int d = (C0041t.d(true, i2) * i3) + i;
        int length2 = bArr.length - (i3 * i2);
        return length2 > 0 ? C0041t.d(true, length2) + d : d;
    }
}
