package main.CombinedObjects;

import org.xbill.DNS.DNSSEC;

import java.io.IOException;

public class SSDMS {

    private int dnssecType = 1;
    private int dnskeyType = 48;
    private int caaType = 257;
    private int tlsaType = 52;
    private int certificateType = 42;

    public SSDMS() {
    }

    public InterfaceMessage getSecurityResponse(String hostname, int type) throws IOException {
        SecurityCheck dnssec = new SecurityCheck(hostname);
        return dnssec.check(hostname, type);
    }

    public String getConvertedMX(String hostname) throws IOException {
        ConvertToMX mx = new ConvertToMX(hostname);
        return mx.convertToMXRecord(hostname);
    }

    public void printInformation(InterfaceMessage hostmx, int type) throws DNSSEC.DNSSECException {
        PrintInformation print = new PrintInformation(hostmx);
        print.printSections(hostmx, type);
    }

    public String getTLSAString(String hostnameMX, String hostname, int port) {
        switch (port) {
            case 25:
                String target25 = "_25._tcp.";
                target25 += hostnameMX;
                return target25;
            case 465:
                String target465 = "_465._tcp.";
                target465 += hostnameMX;
                return target465;
            case 587:
                String target587 = "_587._tcp.";
                target587 += hostnameMX;
                return target587;
            case 443:
                String target443 = "_443._tcp.";
                target443 += hostname;
                return target443;
        }
        return null;
    }

    public boolean getCertificateCheck(String hostname) throws IOException {
        String mx = getConvertedMX(hostname);
        CertificateTransparencyCheck ctCheck = new CertificateTransparencyCheck(mx);
        return ctCheck.checkTransparency();
    }

    public String getAllCertificates(String hostname) throws Exception {
        CertificateTransparencyAll ctCheck = new CertificateTransparencyAll(hostname);
        return ctCheck.getAllTransparencyEntries(hostname);
    }

}
