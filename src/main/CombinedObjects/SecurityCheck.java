package main.CombinedObjects;

import org.xbill.DNS.*;
//import testing.TestingResolver;

import java.io.IOException;
import java.util.List;

public class SecurityCheck {

    String hostname;
    Name name = null;
    int type = 1;
    int dclass = 1;
    InterfaceMessage response = null;

    public SecurityCheck(String hostname) {
        this.hostname = hostname;
    }

    public InterfaceMessage check(String hostname, int type) throws IOException, DNSSEC.DNSSECException {
        SimpleResolver res = new SimpleResolver();
        //TestingResolver res = new TestingResolver();
        name =  InterfaceName.fromString(hostname, Name.root);
        if (type == 1 || type == 48) {
            res.setEDNS(0, 0, 32768, null);
        }
        Record rec = Record.newRecord(name, type, dclass);
        Message query = Message.newQuery(rec);
        //InterfaceMessage response = new TypeMessage(res.send(query, type, hostname));
        InterfaceMessage response = new TypeMessage(res.send(query));
        return response;
    }

    public InterfaceMessage getMessage() {
        return response;
    }


}
