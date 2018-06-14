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
            for (int iterator = 0; iterator < sect1.length; iterator++) {
                Record rec = sect1[iterator];
                switch (type) {
                    case 1:
                        printRRSIGInfo(rec, iterator);
                        printDNSKEYInfo(rec, iterator);
                        break;
                    case 48:
                        printRRSIGInfo(rec, iterator);
                        printDNSKEYInfo(rec, iterator);
                        break;
                    case 257:
                        printCAARECORDInfo(rec, iterator);
                        break;
                    case 52:
                        printTLSAInfo(rec, iterator);
                        break;
                }
            }
            securityRating(message);
        }
    }

    private static void printDNSKEYInfo(Record rec, int iterator) throws DNSSEC.DNSSECException {
        if (rec instanceof DNSKEYRecord) {
            System.out.println("DNSKEYRECORD    " + iterator);
            DNSKEYRecord dnskeyRec = (DNSKEYRecord) rec;
            System.out.println(iterator + "   Public Key : " + dnskeyRec.getPublicKey());
            System.out.print(iterator + "   Algorithm : " + dnskeyRec.getAlgorithm());
            int algorithm = dnskeyRec.getAlgorithm();
            checkAlgorithm(algorithm);
            System.out.print(iterator + "   Flag :  " + dnskeyRec.getFlags());
            int flag = dnskeyRec.getFlags();
            checkFlag(flag);
            System.out.println(iterator + "   Further information : " + dnskeyRec.rdataToString());
            System.out.println(iterator + "   Key ID : " + dnskeyRec.getFootprint());
        }
    }

    private static void checkFlag(int flag) {
        switch (flag) {
            case 257:
                System.out.println("   Key Signing Key (KSK)");
                break;
            case 256:
                System.out.println("   Zone Signing Key (ZSK)");
                break;
        }
    }

    private static void securityRating(InterfaceMessage response) throws DNSSEC.DNSSECException {
        Record[] sect1 = response.getSectionArray(1);
        if (null != sect1) {
            for (int iterator = 0; iterator < sect1.length; iterator++) {
                Record rec = sect1[iterator];
                if (rec instanceof DNSKEYRecord) {
                    DNSKEYRecord dnskeyRec = (DNSKEYRecord) rec;
                    int keySize;
                    System.out.println(" ===============    Security Rating   ===============");
                    System.out.println(" Record:  " + iterator);
                    switch (dnskeyRec.getAlgorithm()) {
                        case DNSSEC.Algorithm.RSAMD5:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("RSA MD5");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use MD5, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.RSASHA1:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("RSA SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.RSASHA256:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("RSA SHA256");
                            if (keySize >= 2048) {
                                if (keySize >= 3072) {
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                } else {
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA, " +
                                            "but for now that's fine. Acceptable until 2030");
                                }
                            } else {
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits RSA is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.RSASHA512:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("RSA SHA512");
                            if (keySize >= 2048) {
                                if (keySize >= 3072) {
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                } else {
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            } else {
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits RSA is not secure enough and so is SHA512 not helpful!");
                            }
                            break;
                        case DNSSEC.Algorithm.RSA_NSEC3_SHA1:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("RSA NSEC3 SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.DSA:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("DSA");
                            if (keySize >= 2048) {
                                if (keySize >= 3072) {
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                } else {
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DSA," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            } else {
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits DSA is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.DSA_NSEC3_SHA1:
                            keySize = ((DSAPublicKey) dnskeyRec.getPublicKey()).getParams().getP().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("DSA NSEC3 SHA1");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Don't use SHA1, it's not longer secure");
                            break;
                        case DNSSEC.Algorithm.ECDSAP256SHA256:
                            keySize = ((ECPublicKey) dnskeyRec.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("ECDSAP256 SHA256");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security");
                            break;
                        case DNSSEC.Algorithm.ECDSAP384SHA384:
                            keySize = ((ECPublicKey) dnskeyRec.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("ECDSAP384 SHA386");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security");
                            break;
                        case DNSSEC.Algorithm.DH:
                            keySize = ((RSAPublicKey) dnskeyRec.getPublicKey()).getModulus().bitLength();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("DH");
                            if (keySize >= 2048) {
                                if (keySize >= 3072) {
                                    System.out.println("Rating: ***");
                                    System.out.println("Hint: Strong Security");
                                } else {
                                    System.out.println("Rating: **");
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DH," +
                                            " but for now that's fine. Acceptable until 2030");
                                }
                            } else {
                                System.out.println("Rating: *");
                                System.out.println("Hint: Less than 2048 Bits DH is not secure enough!");
                            }
                            break;
                        case DNSSEC.Algorithm.INDIRECT:
                            System.out.println("Rating: *");
                            break;
                        case DNSSEC.Algorithm.ECC_GOST:
                            keySize = ((ECPublicKey) dnskeyRec.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("ECC GOST");
                            System.out.println("Rating: ***");
                            System.out.println("Hint: Strong Security!");
                            break;
                        case DNSSEC.Algorithm.PRIVATEDNS:
                            keySize = ((ECPublicKey) dnskeyRec.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " + keySize + " Bits");
                            System.out.println("PRIVATE EDNS");
                            System.out.println("Rating: *");
                            System.out.println("Hint: Weak Security");
                            break;
                        case DNSSEC.Algorithm.PRIVATEOID:
                            keySize = ((ECPublicKey) dnskeyRec.getPublicKey()).getParams().getCurve().getField().getFieldSize();
                            System.out.println("Key size:   " + keySize + " Bits");
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


    private static void printRRSIGInfo(Record rec, int iterator) {
        if (rec instanceof RRSIGRecord) {
            System.out.println("RRSIGRECORD   " + iterator);
            RRSIGRecord rrsigRec = (RRSIGRecord) rec;
            System.out.println(iterator + "   Expire Date of this Record : " + rrsigRec.getExpire());
            System.out.println(iterator + "   Inspection Date of this Record : " + rrsigRec.getTimeSigned());
            System.out.println(iterator + "   Number of Labels : " + rrsigRec.getLabels());
            System.out.println(iterator + "   TTL (Time To Live) : " + rrsigRec.getTTL());
            System.out.println(iterator + "   OrigTTL (Time To Live) : " + rrsigRec.getOrigTTL());
            System.out.println(iterator + "   Signer's Name : " + rrsigRec.getSigner());
            System.out.println(iterator + "   Key Tag (DNSKEY ID) : " + rrsigRec.getFootprint());
            System.out.print(iterator + "   Public Key : " + rrsigRec.getAlgorithm());
            int algorithm = rrsigRec.getAlgorithm();
            checkAlgorithm(algorithm);
        }
    }

    public static void checkAlgorithm(int number) {
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

    private static void printCAARECORDInfo(Record rec, int iterator) {
        if (rec instanceof CAARecord) {
            System.out.println("CAARECORD    " + iterator);
            CAARecord caaRec = (CAARecord) rec;
            System.out.println(iterator + "   Flag:  " + caaRec.getFlags() + "   :   Flag 0 is currently used to represent the critical flag, which isn't in use anymore.");
            System.out.print(iterator + "   Tag:  " + caaRec.getTag());
            String tag = caaRec.getTag();
            checkTag(tag);
            System.out.println(iterator + "   Value:  " + caaRec.getValue() + "   :   The value associated with the tag.");
        }
    }

    private static void checkTag(String tag) {
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

    private static void printTLSAInfo(Record rec, int iterator) {
        if (rec instanceof TLSARecord) {
            System.out.println("TLSARECORD    " + iterator);
            TLSARecord tlsaRec = (TLSARecord) rec;
            System.out.print(iterator + "   CertificateUsage : " + tlsaRec.getCertificateUsage());
            int certificateUsage = tlsaRec.getCertificateUsage();
            checkUsage(certificateUsage);
            System.out.print(iterator + "   Matching Type : " + tlsaRec.getMatchingType());
            int matchingType = tlsaRec.getMatchingType();
            checkType(matchingType);
            System.out.print(iterator + "   Selector : " + tlsaRec.getSelector());
            int selector = tlsaRec.getSelector();
            checkSelector(selector);
            System.out.println(iterator + "   Name : " + tlsaRec.getName());
            System.out.println(iterator + "   TLSA Data : " + tlsaRec.rdataToString());
        }
    }

    private static void checkSelector(int selector) {
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

    private static void checkType(int matchingType) {
        switch (matchingType) {
            case 0:
                System.out.println("   :   A hash is created from the complete certificate. ");
                break;
            case 1:
                System.out.println("   :   Only a hash of the public key and the algorithm is created.");
                break;
        }
    }

    private static void checkUsage(int certificateUsage) {
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
