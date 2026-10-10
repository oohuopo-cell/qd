package defpackage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/* loaded from: assets/cp.dat */
public final class y1 extends AbstractC0030n implements InterfaceC0014f {
    public final AbstractC0045v a;

    public y1(Date date) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + "Z";
        int parseInt = Integer.parseInt(str.substring(0, 4));
        if (parseInt < 1950 || parseInt > 2049) {
            this.a = new C0024k(str);
        } else {
            this.a = new C(str.substring(2));
        }
    }

    public y1(AbstractC0045v abstractC0045v) {
        if (!(abstractC0045v instanceof C) && !(abstractC0045v instanceof C0024k)) {
            throw new IllegalArgumentException("unknown object passed to Time");
        }
        this.a = abstractC0045v;
    }

    public static y1 i(InterfaceC0016g interfaceC0016g) {
        if (interfaceC0016g == null || (interfaceC0016g instanceof y1)) {
            return (y1) interfaceC0016g;
        }
        if (interfaceC0016g instanceof C) {
            return new y1((C) interfaceC0016g);
        }
        if (interfaceC0016g instanceof C0024k) {
            return new y1((C0024k) interfaceC0016g);
        }
        throw new IllegalArgumentException("unknown object in factory: ".concat(interfaceC0016g.getClass().getName()));
    }

    @Override // defpackage.InterfaceC0016g
    public final AbstractC0045v e() {
        return this.a;
    }

    public final String toString() {
        AbstractC0045v abstractC0045v = this.a;
        if (!(abstractC0045v instanceof C)) {
            return ((C0024k) abstractC0045v).u();
        }
        String r = ((C) abstractC0045v).r();
        return r.charAt(0) < '5' ? "20".concat(r) : "19".concat(r);
    }
}
