package defpackage;

/* renamed from: h, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0018h {
    public static final InterfaceC0016g[] d = new InterfaceC0016g[0];
    public InterfaceC0016g[] a;
    public int b;
    public boolean c;

    public C0018h() {
        this(10);
    }

    public C0018h(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("'initialCapacity' must not be negative");
        }
        this.a = i == 0 ? d : new InterfaceC0016g[i];
        this.b = 0;
        this.c = false;
    }

    public final void a(InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        int length = interfaceC0016gArr.length;
        int i = this.b + 1;
        if (this.c | (i > length)) {
            InterfaceC0016g[] interfaceC0016gArr2 = new InterfaceC0016g[Math.max(interfaceC0016gArr.length, (i >> 1) + i)];
            System.arraycopy(this.a, 0, interfaceC0016gArr2, 0, this.b);
            this.a = interfaceC0016gArr2;
            this.c = false;
        }
        this.a[this.b] = interfaceC0016g;
        this.b = i;
    }

    public final InterfaceC0016g b(int i) {
        if (i < this.b) {
            return this.a[i];
        }
        throw new ArrayIndexOutOfBoundsException(i + " >= " + this.b);
    }

    public final InterfaceC0016g[] c() {
        int i = this.b;
        if (i == 0) {
            return d;
        }
        InterfaceC0016g[] interfaceC0016gArr = this.a;
        if (interfaceC0016gArr.length == i) {
            this.c = true;
            return interfaceC0016gArr;
        }
        InterfaceC0016g[] interfaceC0016gArr2 = new InterfaceC0016g[i];
        System.arraycopy(interfaceC0016gArr, 0, interfaceC0016gArr2, 0, i);
        return interfaceC0016gArr2;
    }
}
