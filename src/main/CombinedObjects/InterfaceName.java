package main.CombinedObjects;

import org.xbill.DNS.*;

public interface InterfaceName {
        Name root = null;

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

        static Name
        fromString(String s, Name origin) throws TextParseException {
                if (s.equals("@") && origin != null)
                        return origin;
                else if (s.equals("."))
                        return (root);

                return new Name(s, origin);
        }

        org.xbill.DNS.Name relativize(org.xbill.DNS.Name origin);

        org.xbill.DNS.Name  wild(int n);

        org.xbill.DNS.Name canonicalize();

        org.xbill.DNS.Name fromDNAME(DNAMERecord dname) throws NameTooLongException;

        boolean isWild();


        boolean isAbsolute();


        short length();


        int labels();


        boolean  subdomain(org.xbill.DNS.Name domain);


        String  toString(boolean omitFinalDot);

        String  toString();


        byte [] getLabel(int n);

        String getLabelString(int n);

        void toWire(DNSOutput out, Compression c);

        byte [] toWire();

        void toWireCanonical(DNSOutput out);

        byte [] toWireCanonical();


        void toWire(DNSOutput out, Compression c, boolean canonical);

        /**
         * Are these two Names equivalent?
         */
        boolean equals(Object arg);

        int hashCode();

        int compareTo(Object o);

    }



