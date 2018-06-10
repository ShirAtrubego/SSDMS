package main;

import org.xbill.DNS.Name;

public interface InterfaceRecord {

//        abstract void  rrFromWire(DNSInput in) throws IOException;

//        public static org.xbill.DNS.Record  newRecord(Name name, int type, int dclass, long ttl, int length, byte [] data);        }


//        public static org.xbill.DNS.Record  newRecord(Name name, int type, int dclass, long ttl, byte [] data);


//        public static org.xbill.DNS.Record  newRecord(Name name, int type, int dclass, long ttl);

//        public static org.xbill.DNS.Record newRecord(Name name, int type, int dclass);

//        static org.xbill.DNS.Record  fromWire(DNSInput in, int section, boolean isUpdate) throws IOException;

//        static org.xbill.DNS.Record fromWire(DNSInput in, int section) throws IOException;

//        public static org.xbill.DNS.Record fromWire(byte [] b, int section) throws IOException;

        //       void toWire(DNSOutput out, int section, Compression c);


        byte [] toWire(int section);


        byte [] toWireCanonical();

        byte [] rdataToWireCanonical();


//        abstract String rrToString();


        String rdataToString();

        String toString();


//        abstract void  rdataFromString(Tokenizer st, Name origin) throws IOException;


//        public static org.xbill.DNS.Record  fromString(Name name, int type, int dclass, long ttl, Tokenizer st, Name origin) throws IOException;


//        public static org.xbill.DNS.Record fromString(Name name, int type, int dclass, long ttl, String s, Name origin) throws IOException;


        Name getName();

        int getType();

        int getRRsetType();

        int getDClass();

        long  getTTL() ;

        //       abstract void rrToWire(DNSOutput out, Compression c, boolean canonical);

        boolean sameRRset(org.xbill.DNS.Record rec);

        boolean equals(Object arg);

        int hashCode();

//        org.xbill.DNS.Record cloneRecord();

        org.xbill.DNS.Record withName(Name name);

//        org.xbill.DNS.Record withDClass(int dclass, long ttl) ;

//        void setTTL(long ttl);

        int compareTo(Object o);

        Name getAdditionalName();


//        static int checkU8(String field, int val);

//        static int checkU16(String field, int val);

//        static long checkU32(String field, long val);

//        static Name checkName(String field, Name name);

//        static byte [] checkByteArrayLength(String field, byte [] array, int maxLength);

    }



