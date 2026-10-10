package defpackage;

/* renamed from: j0, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0023j0 extends C0024k {
    public final byte[] A() {
        byte[] bArr = this.a;
        if (bArr[bArr.length - 1] == 90) {
            if (!w()) {
                byte[] bArr2 = new byte[bArr.length + 4];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length - 1);
                System.arraycopy(u1.b("0000Z"), 0, bArr2, bArr.length - 1, 5);
                return bArr2;
            }
            if (!x()) {
                byte[] bArr3 = new byte[bArr.length + 2];
                System.arraycopy(bArr, 0, bArr3, 0, bArr.length - 1);
                System.arraycopy(u1.b("00Z"), 0, bArr3, bArr.length - 1, 3);
                return bArr3;
            }
            if (v()) {
                int length = bArr.length - 2;
                while (length > 0 && bArr[length] == 48) {
                    length--;
                }
                if (bArr[length] == 46) {
                    byte[] bArr4 = new byte[length + 1];
                    System.arraycopy(bArr, 0, bArr4, 0, length);
                    bArr4[length] = 90;
                    return bArr4;
                }
                byte[] bArr5 = new byte[length + 2];
                int i = length + 1;
                System.arraycopy(bArr, 0, bArr5, 0, i);
                bArr5[i] = 90;
                return bArr5;
            }
        }
        return bArr;
    }

    @Override // defpackage.C0024k, defpackage.AbstractC0045v
    public final void j(C0041t c0041t, boolean z) {
        c0041t.j(z, 24, A());
    }

    @Override // defpackage.C0024k, defpackage.AbstractC0045v
    public final int m(boolean z) {
        return C0041t.d(z, A().length);
    }

    @Override // defpackage.C0024k, defpackage.AbstractC0045v
    public final AbstractC0045v p() {
        return this;
    }

    @Override // defpackage.C0024k, defpackage.AbstractC0045v
    public final AbstractC0045v q() {
        return this;
    }
}
