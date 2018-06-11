package main.CombinedObjects;

import org.xbill.DNS.*;
import test.testCombinedObjects.TestingResolver;

import java.io.IOException;
import java.util.List;

public class SecurityCheck {

    String hostname;
    InterfaceName name = null;
    int type = 1;
    int dclass = 1;
    InterfaceMessage response = null;
    InterfaceMessage responseTest = null;

    public SecurityCheck(String hostname) {
        this.hostname = hostname;
    }

    public InterfaceMessage check(String hostname, int type) throws IOException {

//        SimpleResolver res = new SimpleResolver();
        TestingResolver resTest = new TestingResolver();

        name = (InterfaceName) InterfaceName.fromString(hostname, Name.root);
        if (type == 1 || type == 48) {
//            res.setEDNS(0, 0, 32768, null);
            resTest.setEDNS(0, 0, 32768, null);
        }
        Record rec = Record.newRecord((Name) name, type, dclass);
        Message query = Message.newQuery(rec);

        InterfaceMessage responseTest = new TypeMessage(resTest.send(query, type, hostname));
//        InterfaceMessage response = new TypeMessage(res.send(query));

//        return response;
        return responseTest;
    }

    public InterfaceMessage getMessage() {

//        return response;
        return responseTest;
    }


}
