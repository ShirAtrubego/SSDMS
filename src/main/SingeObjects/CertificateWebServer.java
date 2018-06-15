package main.SingeObjects;

import org.bouncycastle.asn1.ASN1Primitive;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


public class CertificateWebServer {


    public void getWebServerCert(String mailServer) throws IOException {


/*
  443 is the network port number used by the SSL https: URi scheme.

*/

        int port = 443;
//            int port = 465;
//            int port = 587;
//            int port = 25;

//        String mailServer = "smtp.hsr.ch";
//        String hostname = "mta5.am0.yahoodns.net";
//        System.out.println("--------------------------------------");
//        System.out.println(CertificateFactory.getInstance(hostname));
//        SMTPClient h = new SMTPClient(); h.connect("mx1.hsr.ch"); h.getRemotePort();
//       System.out.println("--------------------------------------");

        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();

        System.out.println("Creating a SSL Socket For "+mailServer+" on port "+port);

        SSLSocket socket = (SSLSocket) factory.createSocket(mailServer, port);

        socket.startHandshake();
//         System.out.println("Handshaking Complete");

        Certificate[] serverCerts = socket.getSession().getPeerCertificates();
        System.out.println("Retreived Server's Certificate Chain");

        System.out.println(serverCerts.length + " Certifcates Found");
        // for (int i = 0; i < serverCerts.length; i++) {
        Certificate myCert = serverCerts[0];

        // Print Cert
        System.out.println( "Es ist: \n" + myCert.toString());

        System.out.println("========================================================");
        X509Certificate s = (X509Certificate) myCert;

        System.out.println("getCriticalExtensionOIDs: " + s.getCriticalExtensionOIDs());
        System.out.println("========================================================");
        System.out.println("getSigAlgOID: " + s.getSigAlgOID());
        System.out.println("========================================================");
        System.out.println("getExtensionValue: " + s.getExtensionValue("accessMethod"));
        System.out.println("========================================================");
        System.out.println("getNonCriticalExtensionOIDs: " + s.getNonCriticalExtensionOIDs());



        System.out.println("========================================================");
        System.out.println("Vielleicht 1: " + getExtension((X509Certificate) myCert, "1.3.6.1.5.5.7.1.1"));
        System.out.println("========================================================");
        System.out.println("Vielleicht 2: " + getExtension(s, "accessLocation"));
        System.out.println("========================================================");



/*
        System.out.println("Ist es gegangen 1: " + ((X509Certificate) myCert).getIssuerX500Principal().getName());
        System.out.println("Ist es gegangen 2: " +  ((X509Certificate) myCert).getBasicConstraints());
        System.out.println("DN ist: " + ((X509Certificate) myCert).getIssuerDN());
        System.out.println("SubjectDN ist: " + ((X509Certificate) myCert).getSubjectDN()); // TLS A Record 443
*/
//        System.out.println(); // zert von web schauen auf hsr.ch auf port 443 Zerfifiktat oder hash von zert angeben und in zone überprüfen Zone hsr.ch; zertifikat hostname usfiltere mit hostname wissen welche domäne -_> domäneabfrage, wer hat zert ausgegeben Caa records stimmt das? TLS A record richtigs zert? Mix records und A record ist er hostname einer der offizielen Mailserver, IP oder hostname im zert und im MX sieht ob ok
        // nur hostname schwieriger, irgendwie zert beschaffen, verbindung auf 443, kriegt man zert? auf port 25 mit starttls ob starttls unterstützt und zert liefert?


        // Save to file
//        PrintStream printStream = new PrintStream(new FileOutputStream("mail.crt"));
//        System.setOut(printStream);


//       System.out.print(myCert.toString());



        socket.close();

        //stmp.hsr.ch chani signiere vo root ca weni hacke und denn gsehts so us als ob ich es richtigs zert han und cha so zb phishe, mit patchday entfernen hals jahr oder jahr bis sie zertifikate akzeptieren, root ca zert kann man nicht revoken, google im browser checken auf interenn datenbank auf erfahrung
    }


    public ASN1Primitive getExtension(X509Certificate cert, String oid) throws IOException {
        byte[] extBytes = cert.getExtensionValue(oid);
        if (null != extBytes) {
            return (ASN1Primitive) org.bouncycastle.x509.extension.X509ExtensionUtil.fromExtensionValue(extBytes);
        }
        return null;
    }




}
