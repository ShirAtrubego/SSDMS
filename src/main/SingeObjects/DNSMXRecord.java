package main.SingeObjects;

import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Name;

public class DNSMXRecord implements AbstractMXRecord {

    private MXRecord internalMxRecord;

    public DNSMXRecord (MXRecord mxRecord){
        internalMxRecord = mxRecord;
    }


    @Override
    public Name getTarget() {
        return internalMxRecord.getTarget();
    }

    @Override
    public int getPriority() {
        return internalMxRecord.getPriority();
    }


    @Override
    public Name getAdditionalName() {
        return internalMxRecord.getAdditionalName();
    }
}
