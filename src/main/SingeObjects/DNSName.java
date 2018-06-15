package main.SingeObjects;

import org.xbill.DNS.*;

public class DNSName implements AbstractName {

    private Name internalName;

    public DNSName (Name name){
        internalName = name;
    }

    @Override
    public Name relativize(Name origin) {
        return internalName.relativize(origin);
    }

    @Override
    public Name wild(int n) {
        return internalName.wild(n);
    }

    @Override
    public Name canonicalize() {
        return internalName.canonicalize();
    }

    @Override
    public Name fromDNAME(DNAMERecord dname) throws NameTooLongException {
        return internalName.fromDNAME(dname);
    }

    @Override
    public boolean isWild() {
        return internalName.isWild();
    }

    @Override
    public boolean isAbsolute() {
        return internalName.isAbsolute();
    }

    @Override
    public short length() {
        return internalName.length();
    }

    @Override
    public int labels() {
        return internalName.labels();
    }

    @Override
    public boolean subdomain(Name domain) {
        return internalName.subdomain(domain);
    }

    @Override
    public String toString(boolean omitFinalDot) {
        return internalName.toString(omitFinalDot);
    }

    @Override
    public byte[] getLabel(int n) {
        return internalName.getLabel(n);
    }

    @Override
    public String getLabelString(int n) {
        return internalName.getLabelString(n);
    }

    @Override
    public void toWire(DNSOutput out, Compression c) {
        internalName.toWire(out, c);

    }

    @Override
    public byte[] toWire() {
        return internalName.toWire();
    }

    @Override
    public void toWireCanonical(DNSOutput out) {
        internalName.toWireCanonical(out);

    }

    @Override
    public byte[] toWireCanonical() {
        return internalName.toWireCanonical();
    }

    @Override
    public void toWire(DNSOutput out, Compression c, boolean canonical) {
        internalName.toWire(out, c, canonical);
    }

    @Override
    public int compareTo(Object o) {
        return internalName.compareTo(o);
    }
}
