package main.CombinedObjects;

import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.Message;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    static int dnssecType = 1;
    static int dnskeyType = 48;
    static int caaType = 257;
    static int tlsaType = 52;

    public static void main(String[] argv) throws Exception {
        System.err.close();
        System.setErr(System.out);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geben Sie den Domain-Namen ein, welchen Sie testen wollen:   ");
        String hostname = scanner.next();

        System.out.println("Der Sicherheitscheck wird auf " + hostname + " ausgeführt...");

        System.out.println("Welchen Check wollen Sie ausführen? Wählen Sie eine entsprechende Zahl aus:  ");
        System.out.println("DNSSEC: 1");
        System.out.println("DNSKEY: 2");
        System.out.println("CAA: 3");
        System.out.println("TLSA: 4");
        System.out.println("DNSSEC & DNSKEY: 5");
        System.out.println("Certificate Transparency: 6");
        System.out.println("ALLE: 7");

        Scanner typAuswahl = new Scanner(System.in);
        int typ = typAuswahl.nextInt();

        SSDMS check = new SSDMS();

        switch (typ) {
            case 1:
                checkDNSSEC(hostname, check);
                break;
            case 2:
                checkDNSKey(hostname, check);
                break;
            case 3:
                checkCAA(hostname, check);
                break;
            case 4:
                getTLSAString(hostname, check);
                break;
            case 5:
                checkDNSSEC(hostname, check);
                checkDNSKey(hostname, check);
                break;
            case 6:
                checkCertificate(hostname, check);
                break;
            case 7:
                checkDNSSEC(hostname, check);
                checkDNSKey(hostname, check);
                checkCAA(hostname, check);
                checkCertificate(hostname, check);
                getTLSAString(hostname, check);
                break;
            default:
                System.out.println("Sie haben keine gültige Zahl eingegeben!");


        }
    }

        private static void getTLSAString (String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
            System.out.println("Auf welchen Port wollen Sie die TLSA Überprüfung tätigen?");
            System.out.println("Port 25");
            System.out.println("Port 465");
            System.out.println("Port 587");
            System.out.println("Port 443");
            Scanner tlsa = new Scanner(System.in);
            int tlsaPort = tlsa.nextInt();
            String mx = check.getConvertedMX(hostname);
            switch (tlsaPort) {
                case 25:
                    String port25 = check.getTLSAString(mx, hostname, 25);
                    checkTLSA(hostname, check, port25);
                    break;
                case 465:
                    String port465 = check.getTLSAString(mx, hostname, 465);
                    checkTLSA(hostname, check, port465);
                    break;
                case 587:
                    String port587 = check.getTLSAString(mx, hostname, 587);
                    checkTLSA(hostname, check, port587);
                    break;
                case 443:
                    String port443 = check.getTLSAString(mx, hostname, 443);
                    checkTLSA(hostname, check, port443);

                default:
                    System.out.println("Keinen gültigen Port angegeben!");
            }
        }

        private static void checkTLSA (String hostname, SSDMS check, String tlsaString) throws
        DNSSEC.DNSSECException, IOException {
            System.out.println("TLSA wird üebrprüft");
            InterfaceMessage kk = check.getSecurityResponse(tlsaString, tlsaType);
            check.printInformation(kk, 52);
        }

        private static void checkCAA (String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
            System.out.println("CAA wird üebrprüft");
            InterfaceMessage caaHost = check.getSecurityResponse(hostname, caaType);
            check.printInformation(caaHost, caaType);
        }

        private static void checkDNSKey (String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
            System.out.println("DNSKEY wird üebrprüft");
            InterfaceMessage checkdnsey = check.getSecurityResponse(hostname, dnskeyType);
            check.printInformation(checkdnsey, dnskeyType);
            String mx = check.getConvertedMX(hostname);
            InterfaceMessage checkMXdnsey = check.getSecurityResponse(mx, dnskeyType);
            check.printInformation(checkMXdnsey, dnskeyType);
        }

        private static void checkDNSSEC (String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
            System.out.println("DNSSEC wird üebrprüft");
            InterfaceMessage checkdnssec = check.getSecurityResponse(hostname, dnssecType);
            check.printInformation(checkdnssec, dnssecType);
            String mx = check.getConvertedMX(hostname);
            InterfaceMessage checkMXdnssec = check.getSecurityResponse(mx, dnssecType);
            check.printInformation(checkMXdnssec, dnssecType);
        }

        private static void checkCertificate (String hostname, SSDMS check) throws Exception {
            System.out.println("Möchten Sie alle Einträge anschauen oder nur überprüfen, ob sich der Mail-Server im Transparency berfindet?");
            System.out.println("Alle: 1");
            System.out.println("Nur Check: 2");
            System.out.println("Certificate Transparency wird üebrprüft");
            Scanner cert = new Scanner(System.in);
            int certAuswahl = cert.nextInt();
            switch(certAuswahl){
                case 1:
                    checkAllTransparency(hostname, check);
                    break;
                case 2:
                    checkMailTransparency(hostname, check);
                    break;
                default:
                    System.out.println("Keine gültige Zahl ausgewählt");
            }
        }

    private static void checkAllTransparency(String hostname, SSDMS check) throws Exception {
        String certAll = check.getAllCertificates(hostname);
        String[] tokens = certAll.split("\t");
        for (String t : tokens)
            System.out.println(t);
    }

    private static void checkMailTransparency(String hostname, SSDMS check) throws IOException {
        boolean certCheck = check.getCertificateCheck(hostname);
        if (certCheck) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}

