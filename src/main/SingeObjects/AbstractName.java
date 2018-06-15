
package main.SingeObjects;

import org.xbill.DNS.Compression;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.NameTooLongException;

public interface AbstractName {

// Copyright (c) 2004 Brian Wellington (bwelling@xbill.org)

/*

        void
        rrFromWire(DNSInput in) throws IOException;

        void
        rdataFromString(Tokenizer st, Name origin) throws IOException;

        String rrToString();


        void rrToWire(DNSOutput out, Compression c, boolean canonical);
*/




 //       public static org.xbill.DNS.Name fromString(String s, org.xbill.DNS.Name origin);


//        public static org.xbill.DNS.Name fromString(String s);


 //       static org.xbill.DNS.Name fromConstantString(String s);

//       public static org.xbill.DNS.Name concatenate(org.xbill.DNS.Name prefix, org.xbill.DNS.Name suffix);

        public org.xbill.DNS.Name relativize(org.xbill.DNS.Name origin);

        public org.xbill.DNS.Name  wild(int n);

        public org.xbill.DNS.Name canonicalize();

        public org.xbill.DNS.Name fromDNAME(DNAMERecord dname) throws NameTooLongException;

        public boolean isWild();


        public boolean isAbsolute();


        public short length();


        public int labels();


        public boolean  subdomain(org.xbill.DNS.Name domain);


        public String  toString(boolean omitFinalDot);

        public String  toString();


        public byte [] getLabel(int n);

        public String getLabelString(int n);

        public void toWire(DNSOutput out, Compression c);

        public byte [] toWire();

        public void toWireCanonical(DNSOutput out);

        public byte [] toWireCanonical();


        public void toWire(DNSOutput out, Compression c, boolean canonical);

        /**
         * Are these two Names equivalent?
         */
        public boolean equals(Object arg);

        public int hashCode();

        public int compareTo(Object o);

    }


