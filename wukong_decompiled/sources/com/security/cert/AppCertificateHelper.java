package com.security.cert;

import android.content.Context;
import android.content.pm.PackageManager;
import defpackage.D;
import defpackage.H;
import java.io.ByteArrayInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Map;

/* loaded from: assets/cp.dat */
public class AppCertificateHelper {
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0019, code lost:
    
        r4 = r4.getApkContentsSigners();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.security.cert.X509Certificate getCurrentAppCertificate(android.content.pm.PackageManager r4, java.lang.String r5) {
        /*
            java.lang.String r0 = "X.509"
            java.security.cert.CertificateFactory r0 = java.security.cert.CertificateFactory.getInstance(r0)
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 28
            r3 = 0
            if (r1 < r2) goto L34
            r1 = 134217728(0x8000000, float:3.85186E-34)
            android.content.pm.PackageInfo r4 = r4.getPackageInfo(r5, r1)
            android.content.pm.SigningInfo r4 = defpackage.F.a(r4)
            if (r4 == 0) goto L53
            android.content.pm.Signature[] r4 = defpackage.F.b(r4)
            if (r4 == 0) goto L53
            int r5 = r4.length
            if (r5 <= 0) goto L53
            r4 = r4[r3]
            byte[] r4 = r4.toByteArray()
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream
            r5.<init>(r4)
            java.security.cert.Certificate r4 = r0.generateCertificate(r5)
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4
            return r4
        L34:
            r1 = 64
            android.content.pm.PackageInfo r4 = r4.getPackageInfo(r5, r1)
            android.content.pm.Signature[] r4 = r4.signatures
            if (r4 == 0) goto L53
            int r5 = r4.length
            if (r5 <= 0) goto L53
            r4 = r4[r3]
            byte[] r4 = r4.toByteArray()
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream
            r5.<init>(r4)
            java.security.cert.Certificate r4 = r0.generateCertificate(r5)
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4
            return r4
        L53:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.security.cert.AppCertificateHelper.getCurrentAppCertificate(android.content.pm.PackageManager, java.lang.String):java.security.cert.X509Certificate");
    }

    public static String getSignatureSha256Hex(PackageManager packageManager, String str) {
        X509Certificate currentAppCertificate = getCurrentAppCertificate(packageManager, str);
        if (currentAppCertificate == null) {
            return null;
        }
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(currentAppCertificate.getEncoded());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static byte[] resignCert(Context context, byte[] bArr, String str, Map map, long j) {
        KeyPair keyPair;
        try {
            AttestationHelper$AttestationResult c = H.c((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr)));
            String signatureSha256Hex = getSignatureSha256Hex(context.getPackageManager(), str);
            Map map2 = c.others;
            if (map == null) {
                map = c.deviceProperties;
            }
            byte[] e = H.e(str, signatureSha256Hex, map, map2, j, false);
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
                keyPair = keyPairGenerator.generateKeyPair();
            } catch (Exception e2) {
                e2.printStackTrace();
                keyPair = null;
            }
            return D.i((X509Certificate) c.applicationBinding.certificate, e, keyPair.getPrivate(), null).getEncoded();
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:2|3|(1:25)|5|6|(7:11|12|13|14|15|16|17)|24|12|13|14|15|16|17) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0055, code lost:
    
        r0.printStackTrace();
        r11 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] resignCert_hw(android.content.Context r9, byte[] r10, java.lang.String r11, java.util.Map r12, long r13) {
        /*
            r1 = 0
            java.lang.String r0 = "X.509"
            java.security.cert.CertificateFactory r0 = java.security.cert.CertificateFactory.getInstance(r0)     // Catch: java.lang.Exception -> L6e
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Exception -> L6e
            r2.<init>(r10)     // Catch: java.lang.Exception -> L6e
            java.security.cert.Certificate r10 = r0.generateCertificate(r2)     // Catch: java.lang.Exception -> L6e
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch: java.lang.Exception -> L6e
            com.security.cert.AttestationHelper$AttestationResult r10 = defpackage.H.c(r10)     // Catch: java.lang.Exception -> L6e
            android.content.pm.PackageManager r9 = r9.getPackageManager()     // Catch: java.lang.Exception -> L6e
            java.lang.String r3 = getSignatureSha256Hex(r9, r11)     // Catch: java.lang.Exception -> L6e
            java.util.Map r5 = r10.others     // Catch: java.lang.Exception -> L6e
            if (r12 == 0) goto L24
        L22:
            r4 = r12
            goto L27
        L24:
            java.util.Map r12 = r10.deviceProperties     // Catch: java.lang.Exception -> L6e
            goto L22
        L27:
            java.lang.String r9 = "softwareEnforced"
            boolean r9 = defpackage.H.p(r5, r9)     // Catch: java.lang.Exception -> L6e
            if (r9 != 0) goto L3d
            java.lang.String r9 = "teeEnforced"
            boolean r9 = defpackage.H.p(r5, r9)     // Catch: java.lang.Exception -> L6e
            if (r9 == 0) goto L38
            goto L3d
        L38:
            r9 = 0
        L39:
            r8 = r9
            r2 = r11
            r6 = r13
            goto L3f
        L3d:
            r9 = 1
            goto L39
        L3f:
            byte[] r9 = defpackage.H.e(r2, r3, r4, r5, r6, r8)     // Catch: java.lang.Exception -> L6e
            java.lang.String r11 = "RSA"
            java.security.KeyPairGenerator r11 = java.security.KeyPairGenerator.getInstance(r11)     // Catch: java.lang.Exception -> L53
            r12 = 2048(0x800, float:2.87E-42)
            r11.initialize(r12)     // Catch: java.lang.Exception -> L53
            java.security.KeyPair r11 = r11.generateKeyPair()     // Catch: java.lang.Exception -> L53
            goto L59
        L53:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch: java.lang.Exception -> L6e
            r11 = r1
        L59:
            com.security.cert.AttestationHelper$AppBinding r10 = r10.applicationBinding     // Catch: java.lang.Exception -> L6e
            java.security.cert.Certificate r10 = r10.certificate     // Catch: java.lang.Exception -> L6e
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch: java.lang.Exception -> L6e
            java.security.PrivateKey r11 = r11.getPrivate()     // Catch: java.lang.Exception -> L6e
            java.lang.String r12 = "SHA256withRSA"
            java.security.cert.X509Certificate r9 = defpackage.D.i(r10, r9, r11, r12)     // Catch: java.lang.Exception -> L6e
            byte[] r9 = r9.getEncoded()     // Catch: java.lang.Exception -> L6e
            return r9
        L6e:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.security.cert.AppCertificateHelper.resignCert_hw(android.content.Context, byte[], java.lang.String, java.util.Map, long):byte[]");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:2|3|(1:25)|5|6|(7:11|12|13|14|15|16|17)|24|12|13|14|15|16|17) */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0057, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0059, code lost:
    
        r0.printStackTrace();
        r11 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.security.cert.Certificate resignCert_hw2(android.content.Context r9, java.security.cert.Certificate r10, java.lang.String r11, java.util.Map r12, long r13) {
        /*
            r1 = 0
            byte[] r10 = r10.getEncoded()     // Catch: java.lang.Exception -> L6e
            java.lang.String r0 = "X.509"
            java.security.cert.CertificateFactory r0 = java.security.cert.CertificateFactory.getInstance(r0)     // Catch: java.lang.Exception -> L6e
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Exception -> L6e
            r2.<init>(r10)     // Catch: java.lang.Exception -> L6e
            java.security.cert.Certificate r10 = r0.generateCertificate(r2)     // Catch: java.lang.Exception -> L6e
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch: java.lang.Exception -> L6e
            com.security.cert.AttestationHelper$AttestationResult r10 = defpackage.H.c(r10)     // Catch: java.lang.Exception -> L6e
            android.content.pm.PackageManager r9 = r9.getPackageManager()     // Catch: java.lang.Exception -> L6e
            java.lang.String r3 = getSignatureSha256Hex(r9, r11)     // Catch: java.lang.Exception -> L6e
            java.util.Map r5 = r10.others     // Catch: java.lang.Exception -> L6e
            if (r12 == 0) goto L28
        L26:
            r4 = r12
            goto L2b
        L28:
            java.util.Map r12 = r10.deviceProperties     // Catch: java.lang.Exception -> L6e
            goto L26
        L2b:
            java.lang.String r9 = "softwareEnforced"
            boolean r9 = defpackage.H.p(r5, r9)     // Catch: java.lang.Exception -> L6e
            if (r9 != 0) goto L41
            java.lang.String r9 = "teeEnforced"
            boolean r9 = defpackage.H.p(r5, r9)     // Catch: java.lang.Exception -> L6e
            if (r9 == 0) goto L3c
            goto L41
        L3c:
            r9 = 0
        L3d:
            r8 = r9
            r2 = r11
            r6 = r13
            goto L43
        L41:
            r9 = 1
            goto L3d
        L43:
            byte[] r9 = defpackage.H.e(r2, r3, r4, r5, r6, r8)     // Catch: java.lang.Exception -> L6e
            java.lang.String r11 = "RSA"
            java.security.KeyPairGenerator r11 = java.security.KeyPairGenerator.getInstance(r11)     // Catch: java.lang.Exception -> L57
            r12 = 2048(0x800, float:2.87E-42)
            r11.initialize(r12)     // Catch: java.lang.Exception -> L57
            java.security.KeyPair r11 = r11.generateKeyPair()     // Catch: java.lang.Exception -> L57
            goto L5d
        L57:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch: java.lang.Exception -> L6e
            r11 = r1
        L5d:
            com.security.cert.AttestationHelper$AppBinding r10 = r10.applicationBinding     // Catch: java.lang.Exception -> L6e
            java.security.cert.Certificate r10 = r10.certificate     // Catch: java.lang.Exception -> L6e
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch: java.lang.Exception -> L6e
            java.security.PrivateKey r11 = r11.getPrivate()     // Catch: java.lang.Exception -> L6e
            java.lang.String r12 = "SHA256withRSA"
            java.security.cert.X509Certificate r9 = defpackage.D.i(r10, r9, r11, r12)     // Catch: java.lang.Exception -> L6e
            return r9
        L6e:
            r0 = move-exception
            r9 = r0
            r9.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.security.cert.AppCertificateHelper.resignCert_hw2(android.content.Context, java.security.cert.Certificate, java.lang.String, java.util.Map, long):java.security.cert.Certificate");
    }
}
