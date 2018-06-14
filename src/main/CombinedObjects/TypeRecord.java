package main.CombinedObjects;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;

public class TypeRecord implements InterfaceRecord {

    private Record internalRecord;

    public TypeRecord(Record record) {
        internalRecord = record;
    }

    @Override
    public String rdataToString() {
        return internalRecord.rdataToString();
    }

    @Override
    public Name getName() {
        return internalRecord.getName();
    }

    @Override
    public int getType() {
        return internalRecord.getType();
    }

    @Override
    public long getTTL() {
        return internalRecord.getTTL();
    }
}

