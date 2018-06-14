package main.CombinedObjects;

import org.xbill.DNS.DNSSEC;

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
        System.out.println("Enter the domain name you want to test:   ");
        String hostname = scanner.next();

        System.out.println("The security check is performed on  " + hostname);

        System.out.println("Which check do you want to perform? Select an appropriate number:  ");
        System.out.println("DNSSEC: 1");
        System.out.println("DNSKEY: 2");
        System.out.println("CAA: 3");
        System.out.println("TLSA: 4");
        System.out.println("DNSSEC & DNSKEY: 5");
        System.out.println("Certificate Transparency: 6");
        System.out.println("ALL: 7");

        Scanner number = new Scanner(System.in);
        int typ = number.nextInt();

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
                System.out.println("You have not entered a valid number!");

        }
    }


    private static void getTLSAString(String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
        System.out.println("On which port do you want to do the TLSA check?");
        System.out.println("Port 25");
        System.out.println("Port 465");
        System.out.println("Port 587");
        System.out.println("Port 443");
        Scanner tlsaNumber = new Scanner(System.in);
        int tlsaPort = tlsaNumber.nextInt();
        try{
            String mxAddress = check.getConvertedMX(hostname);
            switch (tlsaPort) {
                case 25:
                    String port25 = check.getTLSAString(mxAddress, hostname, 25);
                    checkTLSA(check, port25);
                    break;
                case 465:
                    String port465 = check.getTLSAString(mxAddress, hostname, 465);
                    checkTLSA(check, port465);
                    break;
                case 587:
                    String port587 = check.getTLSAString(mxAddress, hostname, 587);
                    checkTLSA(check, port587);
                    break;
                case 443:
                    String port443 = check.getTLSAString(mxAddress, hostname, 443);
                    checkTLSA(check, port443);

                default:
                    System.out.println("No valid port specified!");
            }
        }
        catch (NullPointerException e) {
            System.out.println("Domain-Name doesn't exist");
        }

    }

    private static void checkTLSA(SSDMS check, String tlsaString) throws DNSSEC.DNSSECException, IOException {
        System.out.println("TLSA is being checked");
        if (check.hasTLSARecord(tlsaString)) {
            InterfaceMessage tlsaMessage = check.getSecurityResponse(tlsaString, tlsaType);
            check.printInformation(tlsaMessage, 52);
        } else {
            System.out.println("No TLSA RECORD");
        }
    }

    private static void checkCAA(String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
        System.out.println("CAA is being checked");
        if (check.hasCAARecord(hostname)) {
            InterfaceMessage caaMessage = check.getSecurityResponse(hostname, caaType);
            check.printInformation(caaMessage, caaType);
        } else {
            System.out.println("No CAA Record!");
        }
    }

    private static void checkDNSKey(String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
        System.out.println("DNSKEY is being checked");
        if (check.hasDNSKEY(hostname)) {
            InterfaceMessage dnskeyMessage = check.getSecurityResponse(hostname, dnskeyType);
            check.printInformation(dnskeyMessage, dnskeyType);
            String mxHost = check.getConvertedMX(hostname);
            InterfaceMessage dnskeyMXMessage = check.getSecurityResponse(mxHost, dnskeyType);
            check.printInformation(dnskeyMXMessage, dnskeyType);
        } else {
            System.out.println("No DNSKEY!");
        }

    }

    private static void checkDNSSEC(String hostname, SSDMS check) throws IOException, DNSSEC.DNSSECException {
        System.out.println("DNSSEC is being checked");
        if (check.hasDNSSEC(hostname)) {
            InterfaceMessage dnssecMessage = check.getSecurityResponse(hostname, dnssecType);
            check.printInformation(dnssecMessage, dnssecType);
            String mxHost = check.getConvertedMX(hostname);
            System.out.println(":  ***********" + "      Host " + mxHost + " has preference " + mxHost + " **************");
            InterfaceMessage dnssecMXMessage = check.getSecurityResponse(mxHost, dnssecType);
            check.printInformation(dnssecMXMessage, dnssecType);
        } else {
            System.out.println("No DNSSEC!");
        }
    }

    private static void checkCertificate(String hostname, SSDMS check) throws Exception {
        System.out.println("Certificate Transparency is being checked");
        System.out.println("Would you like to view all entries or just check whether the mail server is in Transparency?");
        System.out.println("All: 1");
        System.out.println("Just check: 2");
        Scanner certSelection = new Scanner(System.in);
        int certSelect = certSelection.nextInt();
        switch (certSelect) {
            case 1:
                checkAllTransparency(hostname, check);
                break;
            case 2:
                checkMailTransparency(hostname, check);
                break;
            default:
                System.out.println("No valid number selected!");
        }
    }

    private static void checkAllTransparency(String hostname, SSDMS check) throws Exception {
        try{
            String certAll = check.getAllCertificates(hostname);
            String[] tokens = certAll.split("\t");
            for (String checkEntry : tokens)
                System.out.println(checkEntry);
        }
        catch (NullPointerException e) {
            System.out.println("Domain-Name doesn't exist");
        }
    }

    private static void checkMailTransparency(String hostname, SSDMS check) throws IOException {
        try{
            boolean certJustCheck = check.checkTransparencyLog(hostname);
            if (certJustCheck) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
        catch (NullPointerException e) {
            System.out.println("Domain-Name doesn't exist");
        }

    }
}

