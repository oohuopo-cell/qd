package defpackage;

/* loaded from: assets/cp.dat */
public final class o1 extends AbstractC0030n {
    public AbstractC0053z a;

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        return this.a;
    }

    public final I i() {
        InterfaceC0016g[] interfaceC0016gArr = this.a.a;
        if (interfaceC0016gArr.length == 0) {
            return null;
        }
        return I.i(interfaceC0016gArr[0]);
    }

    public final I[] j() {
        AbstractC0053z abstractC0053z = this.a;
        int length = abstractC0053z.a.length;
        I[] iArr = new I[length];
        for (int i = 0; i != length; i++) {
            iArr[i] = I.i(abstractC0053z.a[i]);
        }
        return iArr;
    }
}
