package main.SingeObjects;

import org.xbill.DNS.*;

import java.io.IOException;

public class TLSARecord {
    private Name name = null;
    private int type = 1;
    private int dclass = 1;

    public AbstractMessage checkTLSA(String hostname) throws IOException, DNSSEC.DNSSECException {
        disableWarning();

        SimpleResolver res = new SimpleResolver();
       // TestingResolver resTest = new TestingResolver();

        String nameString = hostname;
        name = Name.fromString(nameString, Name.root);
        type = 52;
        dclass = 1;
        Record tlsaKey = Record.newRecord(name, type, dclass);
        AbstractMessage queryTLSA = new DNSMessage(Message.newQuery(tlsaKey));

        //Message responsetlsaTest = res.send(querytlsa);
        return new DNSMessage(res.send(queryTLSA.toXbillMessage()));

    }

    private void printSections(AbstractMessage message) throws DNSSEC.DNSSECException {
        System.out.println("-------------------------------------------------------TLSA Query-------------------------------------------------------");
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (int i = 0; i < sect1.length; i++) {
                Record r = sect1[i];
                if (r instanceof org.xbill.DNS.TLSARecord) {
                    System.out.println("TLSARECORD    " + i);
                    org.xbill.DNS.TLSARecord dr = (org.xbill.DNS.TLSARecord) r;
                    System.out.print(i + "   CertificateUsage : " + dr.getCertificateUsage());
                    int certificateUsage = dr.getCertificateUsage();
                    checkUsage(certificateUsage);
                    System.out.print(i + "   Matching Type : " + dr.getMatchingType());
                    int matchingType = dr.getMatchingType();
                    checkType(matchingType);
                    System.out.print(i + "   Selector : " + dr.getSelector());
                    int selector = dr.getSelector();
                    checkSelector(selector);
                    System.out.println(i + "   Name : " + dr.getName());
                    System.out.println(i + "   TLSA Data : " + dr.rdataToString());
                }
            }
        }
    }

    private void checkSelector(int selector) {
        switch (selector) {
            case 0:
                System.out.println("   :   The hash contains the complete certificate (not recommended). ");
                break;
            case 1:
                System.out.println("   :   The hash contains a SHA-256 hash.");
                break;
            case 2:
                System.out.println("   :   The hash contains a SHA-512 hash.");
                break;
        }
    }

    private void checkType(int matchingType) {
        switch (matchingType) {
            case 0:
                System.out.println("   :   A hash is created from the complete certificate. ");
                break;
            case 1:
                System.out.println("   :   Only a hash of the public key and the algorithm is created.");
                break;
        }
    }

    private void checkUsage(int certificateUsage) {
        switch (certificateUsage) {
            case 0:
                System.out.println("   :   The hash belongs to the certification authority that can issue certificates for this host. The client must know the certification authority or it must be signed by a trusted certification authority.  ");
                break;
            case 1:
                System.out.println("   :   The hash belongs to the server certificate. It must be signed by a certification authority trusted by the client.  ");
                break;
            case 2:
                System.out.println("   :   The hash belongs to a certification authority that can issue certificates for this host. The client should have your trust even if it is unknown to him and not signed by any known certification authority.  ");
                break;
            case 3:
                System.out.println("   :   The hash belongs to the server certificate and the client should trust it without further checking the trust chain.  ");
                break;
        }
    }

    public void convertToMXRecord (String hostname) throws IOException, DNSSEC.DNSSECException {

        Record[] records = new Lookup(hostname, Type.MX).run();
 //       String tslaString = "";
        for (int i = 0; i < records.length; i++) {
            MXRecord mx = (MXRecord) records[i];
            System.out.println(i + ":  ***********" +"      Host " + mx.getTarget() + " has preference " + mx.getPriority() + " **************");
        String    tslaString = mx.getTarget().toString();
           setPort(tslaString);
        }
//        System.out.println("tslaString ist nachher: " + tslaString);
 //       return tslaString;
    }

    public void printTLSAMxRecord(String hostName) throws IOException, DNSSEC.DNSSECException {
        System.out.println("1");
        convertToMXRecord(hostName);
 //       setPort(s);
        System.out.println("2");
 //       convertToMXRecord(hostName);
      //  setPort();

    }

    private void setPort(String tslaString) throws IOException, DNSSEC.DNSSECException {
        String target25 = "_25._tcp.";
        target25 += tslaString;
        System.out.println("==================================== MX RECORDS TLSA CHECK SMTP PORT 25 PROTOCOL TCP ====================================");
        System.out.println();
        checkTLSA(target25);
        String target465 = "_465._tcp.";
        target465 += tslaString;
        System.out.println("==================================== MX RECORDS TLSA CHECK SMTP PORT 465 PROTOCOL TCP ===================================");
        System.out.println();
        checkTLSA(target465);
        String target587 = "_587._tcp.";
        target587 += tslaString;
        System.out.println("==================================== MX RECORDS TLSA CHECK SMTP PORT 587 PROTOCOL TCP ===================================");
        System.out.println();
        checkTLSA(target587);
    }

    public AbstractMessage checkTLSAFQDN (String hostname) throws IOException, DNSSEC.DNSSECException {
        String target = "_443._tcp.";
        target += hostname;
       return checkTLSA(target);
    }

    private void disableWarning(){
        System.err.close();
        System.setErr(System.out);
    }
}

