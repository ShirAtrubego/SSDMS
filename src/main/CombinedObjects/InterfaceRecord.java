package main.CombinedObjects;

import org.xbill.DNS.Name;

public interface InterfaceRecord {

    String rdataToString();

    Name getName();

    int getType();

    long getTTL();

    int hashCode();
}



