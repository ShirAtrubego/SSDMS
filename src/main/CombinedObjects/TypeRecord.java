package main.CombinedObjects;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;

public class TypeRecord implements InterfaceRecord{

        private Record internalRecord;

        public TypeRecord (Record record){
            internalRecord = record;
        }

        @Override
        public byte[] toWire(int section) {
            return internalRecord.toWire(section);
        }

        @Override
        public byte[] toWireCanonical() {
            return internalRecord.toWireCanonical();
        }

        @Override
        public byte[] rdataToWireCanonical() {
            return internalRecord.rdataToWireCanonical();
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
        public int getRRsetType() {
            return internalRecord.getRRsetType();
        }

        @Override
        public int getDClass() {
            return internalRecord.getDClass();
        }

        @Override
        public long getTTL() {
            return internalRecord.getTTL();
        }

        @Override
        public boolean sameRRset(Record rec) {
            return internalRecord.sameRRset(rec);
        }

        @Override
        public Record withName(Name name) {
            return internalRecord.withName(name);
        }


        @Override
        public int compareTo(Object o) {
            return internalRecord.compareTo(o);
        }

        @Override
        public Name getAdditionalName() {
            return internalRecord.getAdditionalName();
        }
    }

