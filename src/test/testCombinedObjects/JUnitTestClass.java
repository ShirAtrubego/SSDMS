package test.testCombinedObjects;

import main.CombinedObjects.*;

import org.junit.*;
import org.xbill.DNS.*;

import java.io.IOException;

import static org.junit.Assert.*;


public class JUnitTestClass {


    String mailHostName = "mail.weberdns.de.";
    String hostName = "weberdns.de";


    int type1 = 1;
    int type48 = 48;
    int typeInvalid, portInvalid = 42;

    int port25 = 25;
    int port443 = 443;
    int port465 = 465;
    int port587 = 587;


    @BeforeClass
    public static void DisableError() {
        System.err.close();
        System.setErr(System.out);
    }

    /*@Test
    public void mainTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        check.getSecurityResponse(hostName,20);
    }*/

    //DNSSEC Tests


    @Test
    public void checkRecordRRSIG() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        InterfaceMessage message = check.getSecurityResponse(hostName, 1);
        Record[] sect1 = message.getSectionArray(1);
        Record r = sect1[1];
        assertTrue(r instanceof RRSIGRecord);
    }

    @Test
    public void checkHasDNSSECRecordTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        Boolean answer = check.hasDNSSEC(hostName);
        assertTrue(answer);
    }

    @Test
    public void testDNSSECType() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        InterfaceMessage message = check.getSecurityResponse(hostName, 1);
        Record[] sect1 = message.getSectionArray(1);
        Record rec = sect1[1];
        assertEquals(46, rec.getType());
    }

    // DNSKEY Test

    @Test
    public void checkRecordDNSKEY() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        InterfaceMessage message = check.getSecurityResponse(hostName, 48);
        Record[] sect1 = message.getSectionArray(1);
        Record r = sect1[1];
        assertTrue(r instanceof DNSKEYRecord);
    }

    @Test
    public void checkHasDNSKEYRecordTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        Boolean answer = check.hasDNSKEY(hostName);
        assertTrue(answer);
    }

    @Test
    public void testDNSKEYType() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        InterfaceMessage message = check.getSecurityResponse(hostName, 48);
        Record[] sect1 = message.getSectionArray(1);
        Record rec = sect1[1];
        assertEquals(48, rec.getType());
    }

    //CAA Tests

    @Test
    public void checkRecordCAA() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        InterfaceMessage message = check.getSecurityResponse(hostName, 257);
        Record[] sect1 = message.getSectionArray(1);
        Record r = sect1[1];
        assertTrue(r instanceof CAARecord);
    }

    @Test
    public void checkHasCAARecordTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        Boolean answer = check.hasCAARecord(hostName);
        assertTrue(answer);
    }


    //Test MX Converter (you need to have an Internet connection to convert the name in a MX-Adress

    @Test
    public void testMXRecord() throws IOException {
        SSDMS check = new SSDMS();
        String mx = check.getConvertedMX(hostName);
        assertEquals(mailHostName, mx);
    }

    @Test
    public void testMXRecordConverter() throws IOException {
        SSDMS check = new SSDMS();
        String mx = check.getConvertedMX(hostName);
        assertNotEquals("weberdns.de.", mx);
    }

    @Test(expected = NullPointerException.class)
    public void testWrongMXRecord() throws IOException {
        SSDMS check = new SSDMS();
        String mx = check.getConvertedMX("falseName");
    }


    //Test TLSA



    //For this test you need an Internet connection
    @Test
    public void checkRecordTLSA() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        String mx = check.getConvertedMX(hostName);
        String tlsaString = check.getTLSAString(mx, hostName, 25);
        InterfaceMessage message = check.getSecurityResponse(tlsaString, 52);
        Record[] sect1 = message.getSectionArray(1);
        Record r = sect1[0];
        assertTrue(r instanceof TLSARecord);
    }

    @Test
    public void getTLSAString25Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port25);
        assertEquals("_25._tcp.mail.weberdns.de.", tlsa);
    }

    @Test
    public void getWrongTLSAString25Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port25);
        assertNotEquals("_25._tcp.weberdns.de.", tlsa);
    }

    @Test
    public void getTLSAString443Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port443);
        assertEquals("_443._tcp.weberdns.de", tlsa);
    }

    @Test
    public void getWrongTLSAString443Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port443);
        assertNotEquals("_443._tcp.mail.weberdns.de.", tlsa);
    }

    @Test
    public void getTLSAString465Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port465);
        assertEquals("_465._tcp.mail.weberdns.de.", tlsa);
    }

    @Test
    public void getWrongTLSAString465Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port465);
        assertNotEquals("_465._tcp.weberdns.de.", tlsa);
    }

    @Test
    public void getTLSAString587Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port587);
        assertEquals("_587._tcp.mail.weberdns.de.", tlsa);
    }

    @Test
    public void getWrongTLSAString587Test() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, port587);
        assertNotEquals("_587._tcp.weberdns.de.", tlsa);
    }

    @Test
    public void getWrongTLSAPort() {
        SSDMS getTLSA = new SSDMS();
        String tlsa = getTLSA.getTLSAString(mailHostName, hostName, 100);
        assertEquals(null, tlsa);
    }

    @Test
    public void checkHasTLSARecordTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS check = new SSDMS();
        Boolean answer = check.hasTLSARecord("_25._tcp.mail.weberdns.de.");
        assertTrue(answer);
    }

    //Test Certificates (you need to have an Internet connection to check the Transparency Log

    @Test
    public void testCertificate() throws IOException {
        SSDMS check = new SSDMS();
        Boolean TransparencyLog = check.checkTransparencyLog("switch.ch");
        assertTrue(TransparencyLog);
    }

    @Test
    public void testWrongCertificate() throws IOException {
        SSDMS check = new SSDMS();
        Boolean TransparencyLog = check.checkTransparencyLog(hostName);
        assertFalse(TransparencyLog);
    }

    @Test
    public void getCertificateTransparency() throws Exception {
        SSDMS check = new SSDMS();
        String cert = check.getAllCertificates(hostName);
        assertTrue(cert!= null);
    }




    //invalid port
}
