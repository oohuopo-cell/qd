package defpackage;

/* loaded from: assets/cp.dat */
public final class O extends AbstractC0006c {
    public final int d;
    public final AbstractC0006c[] e;

    public O(byte[] bArr, int i) {
        super(bArr, i);
        this.e = null;
        this.d = 1000;
    }

    public O(AbstractC0006c[] abstractC0006cArr) {
        super(t(abstractC0006cArr), false);
        this.e = abstractC0006cArr;
        this.d = 1000;
    }

    public static byte[] t(AbstractC0006c[] abstractC0006cArr) {
        int length = abstractC0006cArr.length;
        if (length == 0) {
            return new byte[]{0};
        }
        if (length == 1) {
            return abstractC0006cArr[0].a;
        }
        int i = length - 1;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            byte[] bArr = abstractC0006cArr[i3].a;
            if (bArr[0] != 0) {
                throw new IllegalArgumentException("only the last nested bitstring can have padding");
            }
            i2 += bArr.length - 1;
        }
        byte[] bArr2 = abstractC0006cArr[i].a;
        byte b = bArr2[0];
        byte[] bArr3 = new byte[i2 + bArr2.length];
        bArr3[0] = b;
        int i4 = 1;
        for (AbstractC0006c abstractC0006c : abstractC0006cArr) {
            byte[] bArr4 = abstractC0006c.a;
            int length2 = bArr4.length - 1;
            System.arraycopy(bArr4, 1, bArr3, i4, length2);
            i4 += length2;
        }
        return bArr3;
    }

    @Override // defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        boolean k = k();
        byte[] bArr = this.a;
        if (!k) {
            int length = bArr.length;
            c0041t.m(z, 3);
            c0041t.h(length);
            c0041t.g(bArr, 0, length);
            return;
        }
        c0041t.m(z, 35);
        c0041t.f(128);
        AbstractC0006c[] abstractC0006cArr = this.e;
        if (abstractC0006cArr != null) {
            c0041t.o(abstractC0006cArr);
        } else if (bArr.length >= 2) {
            byte b = bArr[0];
            int length2 = bArr.length;
            int i = length2 - 1;
            int i2 = this.d;
            int i3 = i2 - 1;
            while (i > i3) {
                c0041t.f(3);
                c0041t.h(i2);
                c0041t.f(0);
                c0041t.g(bArr, length2 - i, i3);
                i -= i3;
            }
            c0041t.f(3);
            c0041t.h(i + 1);
            c0041t.f(b);
            c0041t.g(bArr, length2 - i, i);
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
        AbstractC0006c[] abstractC0006cArr = this.e;
        if (abstractC0006cArr != null) {
            for (AbstractC0006c abstractC0006c : abstractC0006cArr) {
                i += abstractC0006c.m(true);
            }
            return i;
        }
        if (bArr.length < 2) {
            return i;
        }
        int length = bArr.length - 2;
        int i2 = this.d;
        int i3 = i2 - 1;
        int i4 = length / i3;
        return C0041t.d(true, bArr.length - (i3 * i4)) + (C0041t.d(true, i2) * i4) + i;
    }
}
