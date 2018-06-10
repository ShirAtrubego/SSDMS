package main;

import org.xbill.DNS.Message;
//import securitycheck.CertificateWebServer;
//import securitycheck.TLSARecord;

public class Main {

    public static void main(String[] args) throws Exception {

        String hostHuque = "huque.com"; //bonuscard, mail.de, sasis.ch, bonus-services-test.ch, hin.ch, prisma-world.com, securitymonitoring.chm, huque.com
        String hostWeber = "weberdns.de";
        String mxServer = "mx1.hsr.ch";
        String smtpServer = "smtp.hsr.ch";
        String mailServer = "mail.hsr.ch";
        String smtpHost = "mail.smtp.host";
        String smtpSocket = "mail.smtp.ssl.socketFactory.class";
        String smtpSocketPort = "mail.smtp.ssl.socketFactory.port";
        String startTls = "mail.smtp.starttls.enable";
        String value = "true";

        String port = "25";

        int portNumber = 25;
        int first = 1;
        int second = 48;

        String lines = " ------------------------------------------------------- ";

        DNSSECRecord dnsSec = new DNSSECRecord();

        System.out.println(lines + hostWeber + lines);
        System.out.println(lines + "DNSSEC Query" + lines);
        AbstractMessage firstDnsSecMessage = dnsSec.checkDNSSEC(hostWeber, first);
        dnsSec.printDNSSECRecordSections(firstDnsSecMessage);
        System.out.println();
        System.out.println(lines + "DNSKEY Query" + lines);
        AbstractMessage secondtDnsSecMessage = dnsSec.checkDNSSEC(hostWeber, second);
        dnsSec.printDNSSECRecordSections(secondtDnsSecMessage);
    }
}