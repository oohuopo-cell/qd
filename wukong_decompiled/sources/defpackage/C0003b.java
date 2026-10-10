package defpackage;

/* renamed from: b, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0003b {
    public final Class a;
    public final /* synthetic */ int b;

    public C0003b(Class cls, int i) {
        this.b = i;
        this.a = cls;
    }

    public final void a(AbstractC0045v abstractC0045v) {
        if (!this.a.isInstance(abstractC0045v)) {
            throw new IllegalStateException("unexpected object: ".concat(abstractC0045v.getClass().getName()));
        }
    }

    public AbstractC0045v b(AbstractC0051y abstractC0051y) {
        switch (this.b) {
            case 0:
                return abstractC0051y.x();
            case 5:
                return abstractC0051y.y();
            case 6:
                return abstractC0051y;
            case 7:
                return abstractC0051y.z();
            default:
                throw new IllegalStateException("unexpected implicit constructed encoding");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:25:0x0045. Please report as an issue. */
    /* JADX WARN: Type inference failed for: r4v5, types: [y, r0] */
    /* JADX WARN: Type inference failed for: r4v8, types: [y, E0] */
    public final AbstractC0045v c(X x, boolean z) {
        AbstractC0051y abstractC0051y;
        AbstractC0045v b;
        AbstractC0045v abstractC0045v;
        if (128 != x.b) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        InterfaceC0016g interfaceC0016g = x.d;
        if (!z) {
            int i = x.a;
            if (1 == i) {
                throw new IllegalStateException("object explicit - implicit expected.");
            }
            AbstractC0045v e = interfaceC0016g.e();
            if (i != 3) {
                if (i != 4) {
                    a(e);
                    abstractC0045v = e;
                } else if (e instanceof AbstractC0051y) {
                    b = b((AbstractC0051y) e);
                } else {
                    C0033o0 c0033o0 = (C0033o0) e;
                    abstractC0045v = c0033o0;
                    switch (this.b) {
                        case 0:
                            b = AbstractC0006c.r(c0033o0.a);
                            break;
                        case 1:
                            b = C0012e.r(c0033o0.a);
                            break;
                        case 2:
                            abstractC0045v = new C0025k0(c0033o0.a);
                            break;
                        case 3:
                            abstractC0045v = new C0028m(c0033o0.a);
                            break;
                        case 4:
                            byte[] bArr = c0033o0.a;
                            C0036q c0036q = (C0036q) C0036q.c.get(new C0034p(bArr));
                            abstractC0045v = c0036q;
                            if (c0036q == null) {
                                abstractC0045v = new C0036q(bArr, false);
                                break;
                            }
                            break;
                        case 5:
                            break;
                        default:
                            throw new IllegalStateException("unexpected implicit primitive encoding");
                    }
                }
                b = abstractC0045v;
            } else {
                switch (x.e) {
                    case 0:
                        abstractC0051y = new AbstractC0051y(e);
                        break;
                    case 1:
                        ?? abstractC0051y2 = new AbstractC0051y(e);
                        abstractC0051y2.c = -1;
                        abstractC0051y = abstractC0051y2;
                        break;
                    default:
                        ?? abstractC0051y3 = new AbstractC0051y(e);
                        abstractC0051y3.c = -1;
                        abstractC0051y = abstractC0051y3;
                        break;
                }
                b = b(abstractC0051y);
            }
        } else {
            if (!x.t()) {
                throw new IllegalStateException("object explicit - implicit expected.");
            }
            b = interfaceC0016g.e();
            a(b);
        }
        a(b);
        return b;
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }
}
