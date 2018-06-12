package test.testCombinedObjects;

import main.CombinedObjects.*;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.xbill.DNS.DNSSEC;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class JUniTestClass {

    String mailHostName = "mail.weberdns.de";
    String hostName = "weberdns.de";
    String hostnNameInvalid, mailHostNameInvalid = "OneRing";


    int type1 = 1;
    int type48 = 48;
    int typeInvalid, portInvalid = 42;

    int port25 = 25;
    int port443 = 443;
    int port465 = 465;
    int port587 = 587;

    @BeforeClass
    // runs only once, MUST be static
    public static void BeforeClass(){}



    @Before
    // runs before each test
    public void before(){}



    @Test
    public void myTest() throws Exception {
        System.out.println("Nr. 0");
        CertificateTransparencyCheck ich = new CertificateTransparencyCheck("mail.weberdns.de");
        assertEquals(false, ich.checkTransparency());
    }

    /* Testing CombindedObjects.SecurityCheck.java
    *
    *
     */
    @Test
    public void checkType1Test() throws IOException, DNSSEC.DNSSECException {
        SecurityCheck checkTest1 = new SecurityCheck(mailHostName);
        assertEquals("Match",  FileUtils.readFileToString(new File("src/test/testCombinedObjects/dnssecResponse/webermxdns1.txt"),
                "utf-8"),checkTest1.check(mailHostName, type1) );
    }

    @Test
    public void checkType48Test() throws IOException, DNSSEC.DNSSECException {
        SecurityCheck checkTest48 = new SecurityCheck(mailHostName);
        assertEquals("Match",  FileUtils.readFileToString(new File("src/test/testCombinedObjects/dnssecResponse/webermxdns48.txt"),
                "utf-8"), checkTest48.check(mailHostName, type48));
    }

    /* Testing CombindedObjects.SSDMS.java
    *
    *
     */
    @Test
    public void getSecurityResponseType1ValidTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS getDNSSECTest = new SSDMS();
        getDNSSECTest.getSecurityResponse(mailHostName, type1);
    }

    @Test
    public void getSecurityResponseType1InValidTest() throws IOException, DNSSEC.DNSSECException {
        SSDMS getDNSSECTest = new SSDMS();
        getDNSSECTest.getSecurityResponse(mailHostName, typeInvalid);
    }

    @Test
    public void getSecurityResponseType48TValidest() throws IOException, DNSSEC.DNSSECException {
        SSDMS getDNSSECTest = new SSDMS();
        getDNSSECTest.getSecurityResponse(mailHostName, type48);
    }

    @Test
    public void getSecurityResponseType48InTValidest() throws IOException, DNSSEC.DNSSECException {
        SSDMS getDNSSECTest = new SSDMS();
        getDNSSECTest.getSecurityResponse(mailHostName, typeInvalid);
    }

    @Test
    public void getConvertedMXValidTest() throws IOException {
        SSDMS getMX = new SSDMS();
        getMX.getConvertedMX(hostName);
    }

    @Test
    public void getConvertedMXInValidTest() throws IOException {
        SSDMS getMX = new SSDMS();
        getMX.getConvertedMX(hostnNameInvalid);
    }
/*
    @Test
    public void printInformationTest() throws DNSSEC.DNSSECException {
    }
*/
    @Test
    public void getTLSAString25Test() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostName, port25);
    }

    @Test
    public void getTLSAString2443est() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostName, port443);
    }

    @Test
    public void getTLSAString465Test() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostName, port465);
    }

    @Test
    public void getTLSAString587Test() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostName, port587);
    }

    @Test
    public void getTLSAStringInvalidPortTest() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostName, portInvalid);
    }

    @Test
    public void getTLSAStringInvalidHostNameTest() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostName, hostnNameInvalid, port25);
    }

    @Test
    public void getTLSAStringInvalidMailHostTest() {
        SSDMS getTLSA = new SSDMS();
        getTLSA.getTLSAString(mailHostNameInvalid, hostName, port25);
    }

    @Test
    public void getCertificateCheckTest() throws IOException {

    }

    @Test
    public void getAllCertificatesTest() throws Exception {

    }

    @After
    // runs after each test
    public void after(){}

    @AfterClass
    // runs only once, MUST be static
    public static void AfterClass(){}
}
