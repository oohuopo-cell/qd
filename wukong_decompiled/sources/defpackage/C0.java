package defpackage;

/* loaded from: assets/cp.dat */
public abstract class C0 {
    public static final E0 a;
    public static final F0 b;

    /* JADX WARN: Type inference failed for: r0v0, types: [y, E0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [z, F0] */
    static {
        ?? abstractC0051y = new AbstractC0051y();
        abstractC0051y.c = -1;
        a = abstractC0051y;
        ?? abstractC0053z = new AbstractC0053z();
        abstractC0053z.c = -1;
        b = abstractC0053z;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [y, E0] */
    public static E0 a(C0018h c0018h) {
        if (c0018h.b < 1) {
            return a;
        }
        ?? abstractC0051y = new AbstractC0051y(c0018h);
        abstractC0051y.c = -1;
        return abstractC0051y;
    }
}
