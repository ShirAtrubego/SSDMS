package test.testCombinedObjects;

import main.CombinedObjects.*;
import org.junit.*;
import org.xbill.DNS.Message;
import org.xbill.DNS.Record;

import java.io.IOException;

import static org.junit.Assert.*;

public class JUniTestClass {

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
