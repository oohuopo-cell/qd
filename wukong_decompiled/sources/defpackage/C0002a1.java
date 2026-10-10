package defpackage;

import java.io.IOException;
import java.security.cert.CertificateParsingException;

/* renamed from: a1, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0002a1 extends CertificateParsingException {
    public IOException a;

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.a;
    }
}
