package main.CombinedObjects;

import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;

import java.io.IOException;

public class SecurityCheck {

    String hostname;
    Name name = null;
    int type = 1;
    int dclass = 1;
    InterfaceMessage response = null;

    public SecurityCheck(String hostname) {
        this.hostname = hostname;
    }

    public InterfaceMessage check(String hostname, int type) throws IOException {
        SimpleResolver res = new SimpleResolver();
        //TestingResolver res = new TestingResolver();
        name = InterfaceName.fromString(hostname, Name.root);
        if (type == 1 || type == 48) {
            res.setEDNS(0, 0, 32768, null);
        }
        Record rec = Record.newRecord(name, type, dclass);
        Message query = Message.newQuery(rec);
        return new TypeMessage(res.send(query));
        //return new TypeMessage(res.send(query, type, hostname));
    }
}
