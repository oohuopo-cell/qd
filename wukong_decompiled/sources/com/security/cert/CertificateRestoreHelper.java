package com.security.cert;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* loaded from: assets/cp.dat */
public class CertificateRestoreHelper {
    public static X509Certificate restoreFromBase64(String str) {
        return restoreFromBytes(Base64.decode(str, 0));
    }

    public static X509Certificate restoreFromBytes(byte[] bArr) {
        return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
    }

    public static String transBase64StrformCerte(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            return null;
        }
        return Base64.encodeToString(x509Certificate.getEncoded(), 0);
    }
}
