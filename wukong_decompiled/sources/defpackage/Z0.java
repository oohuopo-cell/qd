package defpackage;

import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

/* loaded from: assets/cp.dat */
public final class Z0 extends CertificateException {
    public NoSuchProviderException a;

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
