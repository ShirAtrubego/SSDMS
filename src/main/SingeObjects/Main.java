package main.SingeObjects;

public class Main {

    public static void main(String[] args) throws Exception {

        String hostHuque = "huque.com"; //bonuscard, mail.de, sasis.ch, bonus-services-test.ch, hin.ch, prisma-world.com, securitymonitoring.chm, huque.com
        String hostWeber= "weberdns.de";
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


        CertificateTransparencyALL checkTransparencyAll = new CertificateTransparencyALL();

        checkTransparencyAll.getAllTransparencyEntries(hostWeber);
        checkTransparencyAll.printAllTransparencyEntries();


        CertificateTransparencyCheck checkTransparency = new CertificateTransparencyCheck();
/*
        boolean b = checkTransparency.checkTransparencyEntry(hostWeber);
        if (b){
            System.out.println("Ja");
        }
        else {
            System.out.println("nein");
        }
*/


        CAARecord caaRecord = new CAARecord();

/*
        AbstractMessage caaRecordMessage = caaRecord.getcheckCAARecord(hostWeber);
        System.out.println(lines + hostWeber + lines);
        caaRecord.printCAASections(caaRecordMessage);
*/


         DNSSECRecord dnsSec = new DNSSECRecord();
/*
        System.out.println(lines + hostWeber + lines);
        System.out.println(lines + "DNSSEC Query" + lines);
        AbstractMessage firstDnsSecMessage = dnsSec.checkDNSSEC(hostWeber, first);
        dnsSec.printDNSSECRecordSections(firstDnsSecMessage);
        System.out.println();
        System.out.println(lines + "DNSKEY Query" + lines);
        AbstractMessage secondtDnsSecMessage = dnsSec.checkDNSSEC(hostWeber, second);
        dnsSec.printDNSSECRecordSections(secondtDnsSecMessage);
*/



        TLSARecord tlsa = new TLSARecord();
/*
        System.out.println(lines+ hostWeber + lines);

       tlsa.convertToMXRecord(hostWeber);
        tlsa.checkTLSAFQDN(hostWeber);
    //   tlsa.printTLSAMxRecord(hostWeber);
*/


    CertificateWebServer getHostServerCert = new CertificateWebServer();
/*
    getHostServerCert.getWebServerCert(hostWeber);
*/

/*
        if(args.length>0) {
            mailServer=args[0];
        }
        Properties props = new Properties();
        props.setProperty(smtpHost, mailServer);
        props.setProperty(smtpSocket, "securityCheck.CertificateMailServer");
        props.setProperty(smtpSocketPort, port);
        props.setProperty(startTls, value);

        Session session = Session.getDefaultInstance(props);
        //session.setDebug(true);
        System.out.println("Connecting to "+ mailServer);
        try {
            Transport transport = session.getTransport ();
            transport.connect();
            // here we'll get the output from the CertMailServerCert
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed.");

*/
    }
}
