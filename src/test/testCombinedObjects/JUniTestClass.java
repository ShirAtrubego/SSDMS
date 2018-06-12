package test.testCombinedObjects;

import main.CombinedObjects.*;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.Message;
import org.xbill.DNS.Record;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class JUniTestClass {

    String hostName = "mail.weberdns.de";
    int type1 = 1;
    int type48 = 48;

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

    @Test
    public void checkType1Test() throws IOException, DNSSEC.DNSSECException {
        SecurityCheck checkTest1 = new SecurityCheck(hostName);
        assertEquals("src/test/testCombinedObjects/dnssecResponse/weberdns1.txt",checkTest1.check(hostName, type1) );
    }

    @Test
    public void checkType48Test() throws IOException, DNSSEC.DNSSECException {
        SecurityCheck checkTest48 = new SecurityCheck(hostName);
        assertEquals("Match",  FileUtils.readFileToString(new File("src/test/testCombinedObjects/dnssecResponse/webermxdns48.txt"), "utf-8"), checkTest48.check(hostName, type48));
    }


    /*
    @Test
    public void testCAARecord() throws Exception {
        CAARecord caaRecord = new CAARecord();
        caaRecord.checkCAARecord("weberdns.de");
        //CAARecord d = caaRecord.checkCAARecord("weberdns.de");
    }
    */

    @After
    // runs after each test
    public void after(){}

    @AfterClass
    // runs only once, MUST be static
    public static void AfterClass(){}
}
