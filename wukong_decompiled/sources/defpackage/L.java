package defpackage;

import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: assets/cp.dat */
public final class L extends D {
    public static final Hashtable b;
    public static final Hashtable c;
    public static final L d;
    public final Hashtable a;

    static {
        C0036q a = K.a("2.5.4.6");
        C0036q a2 = K.a("2.5.4.10");
        C0036q a3 = K.a("2.5.4.11");
        C0036q a4 = K.a("2.5.4.12");
        C0036q a5 = K.a("2.5.4.3");
        new C0036q("2.5.4.5").u();
        C0036q u = new C0036q("2.5.4.9").u();
        C0036q a6 = K.a("2.5.4.5");
        C0036q a7 = K.a("2.5.4.7");
        C0036q a8 = K.a("2.5.4.8");
        C0036q a9 = K.a("2.5.4.4");
        C0036q a10 = K.a("2.5.4.42");
        C0036q a11 = K.a("2.5.4.43");
        C0036q a12 = K.a("2.5.4.44");
        C0036q a13 = K.a("2.5.4.45");
        C0036q a14 = K.a("2.5.4.13");
        C0036q a15 = K.a("2.5.4.15");
        C0036q a16 = K.a("2.5.4.17");
        C0036q a17 = K.a("2.5.4.46");
        C0036q a18 = K.a("2.5.4.65");
        C0036q a19 = K.a("2.5.4.72");
        C0036q a20 = K.a("1.3.6.1.5.5.7.9.1");
        C0036q a21 = K.a("1.3.6.1.5.5.7.9.2");
        C0036q a22 = K.a("1.3.6.1.5.5.7.9.3");
        C0036q a23 = K.a("1.3.6.1.5.5.7.9.4");
        C0036q a24 = K.a("1.3.6.1.5.5.7.9.5");
        C0036q a25 = K.a("1.3.36.8.3.14");
        C0036q a26 = K.a("2.5.4.16");
        new C0036q("2.5.4.54").u();
        C0036q c0036q = C1.a;
        C0036q c0036q2 = C1.b;
        C0036q c0036q3 = C1.c;
        C0036q c0036q4 = l1.s;
        C0036q c0036q5 = l1.t;
        C0036q c0036q6 = l1.u;
        C0036q c0036q7 = new C0036q("0.9.2342.19200300.100.1.25");
        C0036q c0036q8 = new C0036q("0.9.2342.19200300.100.1.1");
        Hashtable hashtable = new Hashtable();
        b = hashtable;
        Hashtable hashtable2 = new Hashtable();
        c = hashtable2;
        hashtable.put(a, "C");
        hashtable.put(a2, "O");
        hashtable.put(a4, "T");
        hashtable.put(a3, "OU");
        hashtable.put(a5, "CN");
        hashtable.put(a7, "L");
        hashtable.put(a8, "ST");
        hashtable.put(a6, "SERIALNUMBER");
        hashtable.put(c0036q4, "E");
        hashtable.put(c0036q7, "DC");
        hashtable.put(c0036q8, "UID");
        hashtable.put(u, "STREET");
        hashtable.put(a9, "SURNAME");
        hashtable.put(a10, "GIVENNAME");
        hashtable.put(a11, "INITIALS");
        hashtable.put(a12, "GENERATION");
        hashtable.put(a14, "DESCRIPTION");
        hashtable.put(a19, "ROLE");
        hashtable.put(c0036q6, "unstructuredAddress");
        hashtable.put(c0036q5, "unstructuredName");
        hashtable.put(a13, "UniqueIdentifier");
        hashtable.put(a17, "DN");
        hashtable.put(a18, "Pseudonym");
        hashtable.put(a26, "PostalAddress");
        hashtable.put(a25, "NameAtBirth");
        hashtable.put(a23, "CountryOfCitizenship");
        hashtable.put(a24, "CountryOfResidence");
        hashtable.put(a22, "Gender");
        hashtable.put(a21, "PlaceOfBirth");
        hashtable.put(a20, "DateOfBirth");
        hashtable.put(a16, "PostalCode");
        hashtable.put(a15, "BusinessCategory");
        hashtable.put(c0036q, "TelephoneNumber");
        hashtable.put(c0036q2, "Name");
        hashtable.put(c0036q3, "organizationIdentifier");
        hashtable2.put("c", a);
        hashtable2.put("o", a2);
        hashtable2.put("t", a4);
        hashtable2.put("ou", a3);
        hashtable2.put("cn", a5);
        hashtable2.put("l", a7);
        hashtable2.put("st", a8);
        hashtable2.put("sn", a9);
        hashtable2.put("serialnumber", a6);
        hashtable2.put("street", u);
        hashtable2.put("emailaddress", c0036q4);
        hashtable2.put("dc", c0036q7);
        hashtable2.put("e", c0036q4);
        hashtable2.put("uid", c0036q8);
        hashtable2.put("surname", a9);
        hashtable2.put("givenname", a10);
        hashtable2.put("initials", a11);
        hashtable2.put("generation", a12);
        hashtable2.put("description", a14);
        hashtable2.put("role", a19);
        hashtable2.put("unstructuredaddress", c0036q6);
        hashtable2.put("unstructuredname", c0036q5);
        hashtable2.put("uniqueidentifier", a13);
        hashtable2.put("dn", a17);
        hashtable2.put("pseudonym", a18);
        hashtable2.put("postaladdress", a26);
        hashtable2.put("nameatbirth", a25);
        hashtable2.put("countryofcitizenship", a23);
        hashtable2.put("countryofresidence", a24);
        hashtable2.put("gender", a22);
        hashtable2.put("placeofbirth", a21);
        hashtable2.put("dateofbirth", a20);
        hashtable2.put("postalcode", a16);
        hashtable2.put("businesscategory", a15);
        hashtable2.put("telephonenumber", c0036q);
        hashtable2.put("name", c0036q2);
        hashtable2.put("organizationidentifier", c0036q3);
        d = new L();
    }

    public L() {
        Hashtable hashtable = b;
        Hashtable hashtable2 = new Hashtable();
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            hashtable2.put(nextElement, hashtable.get(nextElement));
        }
        this.a = hashtable2;
        Hashtable hashtable3 = c;
        Hashtable hashtable4 = new Hashtable();
        Enumeration keys2 = hashtable3.keys();
        while (keys2.hasMoreElements()) {
            Object nextElement2 = keys2.nextElement();
            hashtable4.put(nextElement2, hashtable3.get(nextElement2));
        }
    }
}
