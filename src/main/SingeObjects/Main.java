package main.SingeObjects;

import org.xbill.DNS.*;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, DNSSEC.DNSSECException {

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
/*
        System.out.println(lines + hostWeber + lines);
        System.out.println(lines + "DNSSEC Query" + lines);
        InterfaceMessage firstDnsSecMessage = dnsSec.getDNSSECRecord(hostWeber, first);
        dnsSec.printDNSSECRecordSections(firstDnsSecMessage);
        System.out.println();
        System.out.println(lines + "DNSKEY Query" + lines);
        InterfaceMessage secondDnsSecMessage = dnsSec.getDNSSECRecord(hostWeber, second);
        dnsSec.printDNSSECRecordSections(secondDnsSecMessage);
*/

        CAARecord caaRecord = new CAARecord();
/*
        InterfaceMessage caaRecordMessage = caaRecord.getCAARecord(hostWeber);
        System.out.println(lines + hostWeber + lines);
        caaRecord.printCAASections(caaRecordMessage);
*/
        TLSARecord tlsa = new TLSARecord();
/*
        System.out.println(lines+ hostWeber + lines);

        tlsa.convertToMXRecord(hostWeber);
        tlsa.checkTLSAFQDN(hostWeber);
        tlsa.printTLSAMxRecord(hostWeber);
*/
    }
}