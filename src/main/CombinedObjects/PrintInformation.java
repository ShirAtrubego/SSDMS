package main.CombinedObjects;

import org.xbill.DNS.*;

import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

public class PrintInformation {

    InterfaceMessage response;

    public PrintInformation(InterfaceMessage response) {
        this.response = response;
    }

    public void printSections(InterfaceMessage message, int type) throws DNSSEC.DNSSECException {
        Record[] sect1 = message.getSectionArray(1);
        if (null != sect1) {
            for (int i = 0; i < sect1.length; i++) {
                Record r =  sect1[i];
                switch (type) {
                    case 1:
                        printRRSIGInfo(r, i);
                        printDNSKEYInfo(r, i);
                        break;
                    case 48:
                        printRRSIGInfo(r, i);
                        printDNSKEYInfo(r, i);
                        break;
                    case 257:
                        printCAARECORDInfo(r, i);
                        break;
                    case 52:
                        printTLSAInfo(r, i);
                        break;
                }
            }
            securityRating(message);
        }
    }

    private void printDNSKEYInfo(Record r, int i) throws DNSSEC.DNSSECException {
        if (r instanceof DNSKEYRecord) {
            System.out.println("DNSKEYRECORD    " + i);
            DNSKEYRecord dr = (DNSKEYRecord) r; // no begründe
            System.out.println(i + "   Public Key : " + dr.getPublicKey());
            System.out.print(i + "   Algorithm : " + dr.getAlgorithm());
            int algo = dr.getAlgorithm();
            checkAlgorithm(algo);
            System.out.print(i + "   Flag :  " + dr.getFlags());
            int flag = dr.getFlags();
            checkFlag(flag);
            System.out.println(i + "   Weitere Angaben : " + dr.rdataToString());
            System.out.println(i + "   Key ID : " + dr.getFootprint());
        }
    }

    private void checkFlag(int flag) {
        switch (flag) {
            case 257:
                System.out.println("   Key Signing Key (KSK)");
                break;
            case 256:
                System.out.println("   Zone Signing Key (ZSK)");
                break;
        }
    }

    private void securityRating(InterfaceMessage response) throws DNSSEC.DNSSECException {
        Record[] sect1 = response.getSectionArray(1);
        if (null != sect1) {
            for (int i = 0; i < sect1.length; i++) {
                Record r = sect1[i];
                if(r instanceof DNSKEYRecord) {
                    DNSKEYRecord dnskey = (DNSKEYRecord) r;
                    int keysize;
                    System.out.println(" ===============    Security Rating   ===============");
                    System.out.println(" Record:  " + i);
                    switch (dnskey.getAlgorithm()) {
                        case DNSSEC.Algorithm.RSAMD5:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("RSA MD5");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use MD5, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.RSASHA1:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("RSA SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.RSASHA256:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("RSA SHA256");
                            if(keysize >= 2048){
                                if(keysize>= 3072){
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                }
                                else{
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA, " +
                                            "but for now that's fine. Acceptable until 2030");
                                }
                            }
                            else{
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits RSA is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.RSASHA512:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("RSA SHA512");
                            if(keysize >= 2048){
                                if(keysize>= 3072){
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                }
                                else{
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            }
                            else{
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits RSA is not secure enough and so is SHA512 not helpful!");
                            }
                            break;
                        case DNSSEC.Algorithm.RSA_NSEC3_SHA1:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("RSA NSEC3 SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.DSA:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("DSA");
                            if(keysize >= 2048){
                                if(keysize>= 3072){
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                }
                                else{
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DSA," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            }
                            else{
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits DSA is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.DSA_NSEC3_SHA1:
                            keysize = ((DSAPublicKey) dnskey.getPublicKey()).getParams().getP().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("DSA NSEC3 SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.ECDSAP256SHA256:
                            keysize = ((ECPublicKey) dnskey.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("ECDSAP256 SHA256");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security");
                            break;
                        case DNSSEC.Algorithm.ECDSAP384SHA384:
                            keysize = ((ECPublicKey) dnskey.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("ECDSAP384 SHA386");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security");
                            break;
                        case DNSSEC.Algorithm.DH:
                            keysize = ((RSAPublicKey) dnskey.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("DH");
                            if(keysize >= 2048){
                                if(keysize>= 3072){
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                }
                                else{
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DH," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            }
                            else{
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits DH is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.INDIRECT:
                            System.out.println("Rating: *");
                            break;
                        case DNSSEC.Algorithm.ECC_GOST:
                            keysize = ((ECPublicKey) dnskey.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("ECC GOST");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security!");
                            break;
                        case DNSSEC.Algorithm.PRIVATEDNS:
                            keysize = ((ECPublicKey) dnskey.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("PRIVATE EDNS");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Weak Security");
                            break;
                        case DNSSEC.Algorithm.PRIVATEOID:
                            keysize = ((ECPublicKey) dnskey.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " +keysize + " Bits");
                            System.out.println("PRIVATE OID");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Weak Security");
                            break;
                        default:
                    }
                }
            }
        }
    }


    private void printRRSIGInfo(Record r, int i) {
        if (r instanceof RRSIGRecord) {
            System.out.println("RRSIGRECORD   " + i);
            RRSIGRecord rr = (RRSIGRecord) r;
            System.out.println(i + "   Expire Date of this Record : " + rr.getExpire());
            System.out.println(i + "   Inspection Date of this Record : " + rr.getTimeSigned());
            System.out.println(i + "   Number of Labels : " + rr.getLabels());
            System.out.println(i + "   TTL (Time To Live) : " + rr.getTTL());
            System.out.println(i + "   OrigTTL (Time To Live) : " + rr.getOrigTTL());
            System.out.println(i + "   Signer's Name : " + rr.getSigner());
            System.out.println(i + "   Key Tag (DNSKEY ID) : " + rr.getFootprint());
            System.out.print(i + "   Public Key : " + rr.getAlgorithm());
            int algo = rr.getAlgorithm();
            checkAlgorithm(algo);
        }
    }

    public void checkAlgorithm(int number) {
        System.out.print("   Algorithm:   ");
        switch (number) {
            case 1:
                System.out.println("MD5withRSA");
                break;
            case 2:
            case 4:
            case 9:
            case 11:
            case 3:
            case 6:
                System.out.println("SHA1withDSA");
                break;
            case 5:
            case 7:
                System.out.println("SHA1withRSA");
                break;
            case 8:
                System.out.println("SHA256withRSA");
                break;
            case 10:
                System.out.println("SHA512withRSA");
                break;
            case 12:
                System.out.println("GOST3411withECGOST3410");
                break;
            case 13:
                System.out.println("SHA256withECDSA");
                break;
            case 14:
                System.out.println("SHA384withECDSA");
                break;
        }
    }

    private void printCAARECORDInfo(Record r, int i) {
        if (r instanceof CAARecord) {
            System.out.println("CAARECORD    " + i);
            CAARecord dr = (CAARecord) r;
            System.out.println(i + "   Flag:  " + dr.getFlags() + "   :   Flag 0 is currently used to represent the critical flag, which isn't in use anymore.");
            System.out.print(i + "   Tag:  " + dr.getTag());
            String tag = dr.getTag();
            checkTag(tag);
            System.out.println(i + "   Value:  " + dr.getValue() + "   :   The value associated with the tag.");
        }
    }

    private void checkTag(String tag) {
        switch (tag) {
            case "issue":
                System.out.println("   :   explicity authorizes a single certificate authority to issue a certificate (any type) for the hostname.");
                break;
            case "issuewild":
                System.out.println("   :   explicity authorizes a single certificate authority to issue a wildcard certificate (and only wildcard) for the hostname.");
                break;
            case "iodef":
                System.out.println("   :   specifies an URL to which a certificate authority may report policy violations.");
                break;
        }
    }

    private void printTLSAInfo(Record r, int i) {
        if (r instanceof TLSARecord) {
            System.out.println("TLSARECORD    " + i);
            TLSARecord dr = (TLSARecord) r;
            System.out.print(i + "   CertificateUsage : " + dr.getCertificateUsage());
            int certificateUsage = dr.getCertificateUsage();
            checkUsage(certificateUsage);
            System.out.print(i + "   Matching Type : " + dr.getMatchingType());
            int matchingType = dr.getMatchingType();
            checkType(matchingType);
            System.out.print(i + "   Selector : " + dr.getSelector());
            int selector = dr.getSelector();
            checkSelector(selector);
            System.out.println(i + "   Name : " + dr.getName());
            System.out.println(i + "   TLSA Data : " + dr.rdataToString());
        }
    }

    private void checkSelector(int selector) {
        switch (selector) {
            case 0:
                System.out.println("   :   The hash contains the complete certificate (not recommended). ");
                break;
            case 1:
                System.out.println("   :   The hash contains a SHA-256 hash.");
                break;
            case 2:
                System.out.println("   :   The hash contains a SHA-512 hash.");
                break;
        }
    }

    private void checkType(int matchingType) {
        switch (matchingType) {
            case 0:
                System.out.println("   :   A hash is created from the complete certificate. ");
                break;
            case 1:
                System.out.println("   :   Only a hash of the public key and the algorithm is created.");
                break;
        }
    }

    private void checkUsage(int certificateUsage) {
        switch (certificateUsage) {
            case 0:
                System.out.println("   :   The hash belongs to the certification authority that can issue certificates for this host. The client must know the " +
                        "certification authority or it must be signed by a trusted certification authority.  ");
                break;
            case 1:
                System.out.println("   :   The hash belongs to the server certificate. It must be signed by a certification authority trusted by the client.  ");
                break;
            case 2:
                System.out.println("   :   The hash belongs to a certification authority that can issue certificates for this host. The client should have your " +
                        "trust even if it is unknown to him and not signed by any known certification authority.  ");
                break;
            case 3:
                System.out.println("   :   The hash belongs to the server certificate and the client should trust it without further checking the trust chain.  ");
                break;
        }
    }
}
