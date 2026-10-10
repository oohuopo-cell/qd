package com.security.cert;

import java.security.cert.Certificate;

/* loaded from: assets/cp.dat */
public class AttestationHelper$AppBinding {
    public final Certificate certificate;
    public final String packageName;
    public final String signatureSha256Hex;
    public final long versionCode;

    public AttestationHelper$AppBinding(String str, String str2, long j, Certificate certificate) {
        this.packageName = str;
        this.signatureSha256Hex = str2;
        this.versionCode = j;
        this.certificate = certificate;
    }
}
