package main.SingeObjects;

import org.xbill.DNS.Name;

public interface InterfaceMXRecord {

//        Record getObject();

        Name getTarget();

        int getPriority();

//        void rrToWire(DNSOutput out, Compression c, boolean canonical);

        Name getAdditionalName();



    }

