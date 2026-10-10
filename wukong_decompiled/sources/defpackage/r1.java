package defpackage;

/* loaded from: assets/cp.dat */
public final class r1 extends RuntimeException {
    public final Exception a;

    public r1(String str, Exception exc) {
        super(str);
        this.a = exc;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
