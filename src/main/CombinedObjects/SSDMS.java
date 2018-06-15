package main.CombinedObjects;


import org.xbill.DNS.*;

import java.io.IOException;

public class SSDMS {

    private int dnssecType = 1;
    private int dnskeyType = 48;
    private int caaType = 257;
    private int tlsaType = 52;
    private int certificateType = 42;

    public SSDMS() {
    }

    public Boolean hasDNSSEC(String hostname) throws IOException, DNSSEC.DNSSECException {
        InterfaceMessage message = getSecurityResponse(hostname, dnssecType);
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (Record r : sect1) {
                if (r instanceof RRSIGRecord) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Boolean hasDNSKEY(String hostname) throws IOException, DNSSEC.DNSSECException {
        InterfaceMessage message = getSecurityResponse(hostname, dnskeyType);
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (Record r : sect1) {
                if (r instanceof DNSKEYRecord) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Boolean hasTLSARecord(String hostname) throws IOException, DNSSEC.DNSSECException {
        InterfaceMessage message = getSecurityResponse(hostname, tlsaType);
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (Record r : sect1) {
                if (r instanceof TLSARecord) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Boolean hasCAARecord(String hostname) throws IOException, DNSSEC.DNSSECException {
        InterfaceMessage message = getSecurityResponse(hostname, caaType);
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (Record r : sect1) {
                if (r instanceof CAARecord) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public InterfaceMessage getSecurityResponse(String hostname, int type) throws IOException, DNSSEC.DNSSECException {
        SecurityCheck secCheck = new SecurityCheck(hostname);
        return secCheck.check(hostname, type);
    }

    public String getConvertedMX(String hostname) throws IOException {
        ConvertToMX mxHost = new ConvertToMX(hostname);
        return mxHost.convertToMXRecord(hostname);
    }

    public void printInformation(InterfaceMessage message, int type) throws DNSSEC.DNSSECException {
        PrintInformation print = new PrintInformation(message);
        print.printSections(message, type);
    }

    public String getTLSAString(String mxHost, String hostname, int port) {
        switch (port) {
            case 25:
                String tlsaString25 = "_25._tcp.";
                tlsaString25 += mxHost;
                return tlsaString25;
            case 465:
                String tlsaString465 = "_465._tcp.";
                tlsaString465 += mxHost;
                return tlsaString465;
            case 587:
                String tlsaString587 = "_587._tcp.";
                tlsaString587 += mxHost;
                return tlsaString587;
            case 443:
                String tlsaString443 = "_443._tcp.";
                tlsaString443 += hostname;
                return tlsaString443;
        }
        return null;
    }

    public boolean checkTransparencyLog(String hostname) throws IOException {
        String mxHost = getConvertedMX(hostname);
        CertificateTransparencyLog certificateCheck = new CertificateTransparencyLog(mxHost);
        return certificateCheck.checkTransparency();
    }

    public String getAllCertificates(String hostname) throws Exception {
        CertificateTransparencyAll certificateCheck = new CertificateTransparencyAll(hostname);
        return certificateCheck.getAllTransparencyEntries(hostname);
    }
}