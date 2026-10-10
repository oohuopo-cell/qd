package defpackage;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: l, reason: case insensitive filesystem */
/* loaded from: assets/cp.dat */
public final class C0026l extends FilterInputStream {
    public final int a;
    public final boolean b;
    public final byte[][] c;

    public C0026l(ByteArrayInputStream byteArrayInputStream, int i, boolean z) {
        this(byteArrayInputStream, i, z, new byte[11]);
    }

    public C0026l(InputStream inputStream, int i, boolean z, byte[][] bArr) {
        super(inputStream);
        this.a = i;
        this.b = z;
        this.c = bArr;
    }

    public C0026l(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length, false);
    }

    public C0026l(byte[] bArr, int i) {
        this(new ByteArrayInputStream(bArr), bArr.length, true);
    }

    public static AbstractC0045v b(int i, L0 l0, byte[][] bArr) {
        int i2 = 0;
        switch (i) {
            case 1:
                return C0012e.r(c(l0, bArr));
            case 2:
                return new C0028m(l0.b());
            case 3:
                return AbstractC0006c.r(l0.b());
            case 4:
                return new r(l0.b());
            case 5:
                if (l0.b().length == 0) {
                    return C0029m0.a;
                }
                throw new IllegalStateException("malformed NULL encoding encountered");
            case 6:
                byte[] c = c(l0, bArr);
                ConcurrentHashMap concurrentHashMap = C0036q.c;
                C0036q c0036q = (C0036q) C0036q.c.get(new C0034p(c));
                return c0036q == null ? new C0036q(c, true) : c0036q;
            case 7:
                return new C0032o(new C0025k0(l0.b()));
            case 8:
            case 9:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 29:
            default:
                throw new IOException("unknown tag " + i + " encountered");
            case 10:
                byte[] c2 = c(l0, bArr);
                if (c2.length > 1) {
                    return new C0020i(c2, true);
                }
                if (c2.length == 0) {
                    throw new IllegalArgumentException("ENUMERATED has zero length");
                }
                int i3 = c2[0] & 255;
                if (i3 >= 12) {
                    return new C0020i(c2, true);
                }
                C0020i[] c0020iArr = C0020i.b;
                C0020i c0020i = c0020iArr[i3];
                if (c0020i == null) {
                    c0020i = new C0020i(c2, true);
                    c0020iArr[i3] = c0020i;
                }
                return c0020i;
            case 12:
                return new C0046v0(l0.b());
            case 13:
                return new C0047w(l0.b());
            case 18:
                return new C0031n0(l0.b());
            case 19:
                return new C0037q0(l0.b());
            case 20:
                return new C0042t0(l0.b());
            case 21:
                return new C0050x0(l0.b());
            case 22:
                return new C0027l0(l0.b());
            case 23:
                return new C(l0.b());
            case 24:
                return new C0024k(l0.b());
            case 25:
                return new C0025k0(l0.b());
            case 26:
                return new C0052y0(l0.b());
            case 27:
                return new C0021i0(l0.b());
            case 28:
                return new C0048w0(l0.b());
            case 30:
                int i4 = l0.d;
                if ((i4 & 1) != 0) {
                    throw new IOException("malformed BMPString encoding encountered");
                }
                int i5 = i4 / 2;
                char[] cArr = new char[i5];
                byte[] bArr2 = new byte[8];
                int i6 = 0;
                while (i4 >= 8) {
                    if (D.h(l0, bArr2, 8) != 8) {
                        throw new EOFException("EOF encountered in middle of BMPString");
                    }
                    cArr[i6] = (char) ((bArr2[0] << 8) | (bArr2[1] & 255));
                    cArr[i6 + 1] = (char) ((bArr2[2] << 8) | (bArr2[3] & 255));
                    cArr[i6 + 2] = (char) ((bArr2[4] << 8) | (bArr2[5] & 255));
                    cArr[i6 + 3] = (char) ((bArr2[6] << 8) | (bArr2[7] & 255));
                    i6 += 4;
                    i4 -= 8;
                }
                if (i4 > 0) {
                    if (D.h(l0, bArr2, i4) != i4) {
                        throw new EOFException("EOF encountered in middle of BMPString");
                    }
                    do {
                        int i7 = i2 + 1;
                        int i8 = bArr2[i2] << 8;
                        i2 += 2;
                        cArr[i6] = (char) ((bArr2[i7] & 255) | i8);
                        i6++;
                    } while (i2 < i4);
                }
                if (l0.d == 0 && i5 == i6) {
                    return new C0015f0(cArr);
                }
                throw new IllegalStateException();
        }
    }

    public static byte[] c(L0 l0, byte[][] bArr) {
        int i = l0.d;
        if (i >= bArr.length) {
            return l0.b();
        }
        byte[] bArr2 = bArr[i];
        if (bArr2 == null) {
            bArr2 = new byte[i];
            bArr[i] = bArr2;
        }
        if (i != bArr2.length) {
            throw new IllegalArgumentException("buffer length not right for data");
        }
        if (i == 0) {
            return bArr2;
        }
        int i2 = l0.b;
        if (i >= i2) {
            throw new IOException("corrupted stream - out of bounds length found: " + l0.d + " >= " + i2);
        }
        int h = i - D.h(l0.a, bArr2, bArr2.length);
        l0.d = h;
        if (h == 0) {
            l0.a();
            return bArr2;
        }
        throw new EOFException("DEF length " + l0.c + " object truncated by " + l0.d);
    }

    public static int d(InputStream inputStream, int i, boolean z) {
        int read = inputStream.read();
        if ((read >>> 7) == 0) {
            return read;
        }
        if (128 == read) {
            return -1;
        }
        if (read < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (255 == read) {
            throw new IOException("invalid long form definite-length 0xFF");
        }
        int i2 = read & 127;
        int i3 = 0;
        int i4 = 0;
        do {
            int read2 = inputStream.read();
            if (read2 < 0) {
                throw new EOFException("EOF found reading length");
            }
            if ((i3 >>> 23) != 0) {
                throw new IOException("long form definite-length more than 31 bits");
            }
            i3 = (i3 << 8) + read2;
            i4++;
        } while (i4 < i2);
        if (i3 < i || z) {
            return i3;
        }
        throw new IOException("corrupted stream - out of bounds length found: " + i3 + " >= " + i);
    }

    public static int f(InputStream inputStream, int i) {
        int i2 = i & 31;
        if (i2 != 31) {
            return i2;
        }
        int read = inputStream.read();
        if (read < 31) {
            if (read < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
            throw new IOException("corrupted stream - high tag number < 31 found");
        }
        if ((read & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i3 = 0;
        while ((read & 128) != 0) {
            if ((i3 >>> 24) != 0) {
                throw new IOException("Tag number more than 31 bits");
            }
            i3 = ((read & 127) | i3) << 7;
            read = inputStream.read();
            if (read < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
        }
        return (read & 127) | i3;
    }

    /* JADX WARN: Type inference failed for: r7v16, types: [v, y, d1] */
    /* JADX WARN: Type inference failed for: r8v17, types: [v, z, F0] */
    public final AbstractC0045v a(int i, int i2, int i3) {
        L0 l0 = new L0(this, i3, this.a);
        if ((i & 224) == 0) {
            return b(i2, l0, this.c);
        }
        int i4 = i & 192;
        int i5 = 4;
        if (i4 != 0) {
            if ((i & 32) != 0) {
                return X.r(i4, i2, g(l0));
            }
            X x = new X(i5, i4, i2, new r(l0.b()), 2);
            return i4 != 64 ? x : new AbstractC0000a(x);
        }
        int i6 = 0;
        if (i2 == 3) {
            C0018h g = g(l0);
            int i7 = g.b;
            AbstractC0006c[] abstractC0006cArr = new AbstractC0006c[i7];
            while (i6 != i7) {
                InterfaceC0016g b = g.b(i6);
                if (!(b instanceof AbstractC0006c)) {
                    throw new IOException("unknown object encountered in constructed BIT STRING: " + b.getClass());
                }
                abstractC0006cArr[i6] = (AbstractC0006c) b;
                i6++;
            }
            return new O(abstractC0006cArr);
        }
        if (i2 == 4) {
            C0018h g2 = g(l0);
            int i8 = g2.b;
            r[] rVarArr = new r[i8];
            while (i6 != i8) {
                InterfaceC0016g b2 = g2.b(i6);
                if (!(b2 instanceof r)) {
                    throw new IOException("unknown object encountered in constructed OCTET STRING: " + b2.getClass());
                }
                rVarArr[i6] = (r) b2;
                i6++;
            }
            return new S(S.s(rVarArr), rVarArr);
        }
        if (i2 == 8) {
            E0 a = C0.a(g(l0));
            a.getClass();
            return new C0019h0(a);
        }
        if (i2 == 16) {
            if (l0.d < 1) {
                return C0.a;
            }
            if (!this.b) {
                return C0.a(g(l0));
            }
            byte[] b3 = l0.b();
            ?? abstractC0051y = new AbstractC0051y();
            abstractC0051y.c = b3;
            return abstractC0051y;
        }
        if (i2 != 17) {
            throw new IOException("unknown tag " + i2 + " encountered");
        }
        C0018h g3 = g(l0);
        E0 e0 = C0.a;
        if (g3.b < 1) {
            return C0.b;
        }
        ?? abstractC0053z = new AbstractC0053z(g3);
        abstractC0053z.c = -1;
        return abstractC0053z;
    }

    public final AbstractC0045v e() {
        int read = read();
        if (read <= 0) {
            if (read != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int f = f(this, read);
        int i = this.a;
        int d = d(this, i, false);
        if (d >= 0) {
            try {
                return a(read, f, d);
            } catch (IllegalArgumentException e) {
                throw new C0022j("corrupted stream detected", e);
            }
        }
        if ((read & 32) == 0) {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        A a = new A(new X0(this, i), i, this.c);
        int i2 = read & 192;
        if (i2 != 0) {
            return a.b(i2, f);
        }
        if (f == 3) {
            return P.a(a);
        }
        if (f == 4) {
            return T.a(a);
        }
        if (f == 8) {
            return V.g(a);
        }
        if (f == 16) {
            return new AbstractC0051y(a.c());
        }
        if (f == 17) {
            return new AbstractC0053z(a.c());
        }
        throw new IOException("unknown BER object encountered");
    }

    public final C0018h g(L0 l0) {
        C0026l c0026l;
        AbstractC0045v e;
        int i = l0.d;
        if (i >= 1 && (e = (c0026l = new C0026l(l0, i, this.b, this.c)).e()) != null) {
            C0018h c0018h = new C0018h();
            do {
                c0018h.a(e);
                e = c0026l.e();
            } while (e != null);
            return c0018h;
        }
        return new C0018h(0);
    }
}
