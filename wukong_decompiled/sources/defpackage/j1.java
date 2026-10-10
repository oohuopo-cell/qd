package defpackage;

import java.security.GeneralSecurityException;

/* loaded from: assets/cp.dat */
public final class j1 extends Exception {
    public GeneralSecurityException a;

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
