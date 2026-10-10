package defpackage;

/* renamed from: u, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0043u extends IllegalStateException {
    public final /* synthetic */ int a;
    public Exception b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0043u(String str, int i) {
        super(str);
        this.a = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0043u(String str, Exception exc) {
        super(str);
        this.a = 0;
        this.b = exc;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        switch (this.a) {
            case 0:
                return this.b;
            case 1:
                return this.b;
            default:
                return this.b;
        }
    }
}
