package defpackage;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* renamed from: x, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0049x implements Enumeration {
    public int a = 0;
    public final /* synthetic */ AbstractC0051y b;

    public C0049x(AbstractC0051y abstractC0051y) {
        this.b = abstractC0051y;
    }

    @Override // java.util.Enumeration
    public final boolean hasMoreElements() {
        return this.a < this.b.a.length;
    }

    @Override // java.util.Enumeration
    public final Object nextElement() {
        int i = this.a;
        InterfaceC0016g[] interfaceC0016gArr = this.b.a;
        if (i >= interfaceC0016gArr.length) {
            throw new NoSuchElementException();
        }
        this.a = i + 1;
        return interfaceC0016gArr[i];
    }
}
