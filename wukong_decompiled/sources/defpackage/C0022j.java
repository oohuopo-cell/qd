package defpackage;

import java.io.IOException;

/* renamed from: j, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0022j extends IOException {
    public final IllegalArgumentException a;

    public C0022j(String str, IllegalArgumentException illegalArgumentException) {
        super(str);
        this.a = illegalArgumentException;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
