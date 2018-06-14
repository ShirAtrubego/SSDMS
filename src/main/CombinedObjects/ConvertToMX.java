package main.CombinedObjects;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.IOException;

public class ConvertToMX {

    String hostname;

    public ConvertToMX(String hostname) {
        this.hostname = hostname;
    }

    public String convertToMXRecord(String hostname) throws IOException {

        Record[] records = new Lookup(hostname, Type.MX).run();
        for (Record record : records) {
            MXRecord mxHost = (MXRecord) record;
            return mxHost.getTarget().toString();
        }
        return null;
    }
}
