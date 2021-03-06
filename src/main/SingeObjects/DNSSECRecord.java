package main.SingeObjects;

import org.xbill.DNS.*;

import java.io.IOException;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class DNSSECRecord {

    private Name name = null;
    private int type = 1;
    private int dclass = 1;

    public AbstractMessage checkDNSSEC(String hostname, int type) throws IOException, DNSSEC.DNSSECException {
        disableWarning();

        SimpleResolver res = new SimpleResolver();
        //TestingResolver res = new TestingResolver();

        name = Name.fromString(hostname, Name.root);
        res.setEDNS(0, 0, 32768, null);
        Record dnssecKey = Record.newRecord(name, type, dclass);
        AbstractMessage queryDNSSEC = new DNSMessage(Message.newQuery(dnssecKey));

        //Message response = res.send(query, type, hostname);
        AbstractMessage responseDNSSEC = new DNSMessage( res.send(queryDNSSEC.toXbillMessage()) );

        return responseDNSSEC;

    }

    private void printSecurityRating(AbstractMessage response) throws DNSSEC.DNSSECException {
        Record[] sect1 = response.getSectionArray(1);
        if (null != sect1) {
            for (int i = 0; i < sect1.length; i++) {
                Record r = sect1[i];
                if(r instanceof DNSKEYRecord) {
                    DNSKEYRecord dnskey = (DNSKEYRecord) r;
                    int keysize;
                    System.out.println(" ===============    Security Rating   ===============");
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
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA, but for now that's fine. Acceptable until 2030");
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
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit RSA, but for now that's fine. Acceptable until 2030");
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
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DSA, but for now that's fine. Acceptable until 2030");
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
                                    System.out.println("Hint: If you want a signature you can trust for 30 years or more, you might want to use something stronger than 2048-bit DH, but for now that's fine. Acceptable until 2030");
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

    public void printDNSSECRecordSections(AbstractMessage message) throws DNSSEC.DNSSECException {
        Record[] sect1 = message.getSectionArray(1);if(null != sect1){
            for(int i = 0; i < sect1.length; i++){
                Record r = sect1[i];
                if(r instanceof DNSKEYRecord) {
                    System.out.println("DNSKEYRECORD    " + i);
                    DNSKEYRecord dr = (DNSKEYRecord)r; // no begründe
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
                if(r instanceof RRSIGRecord){
                    System.out.println("RRSIGRECORD   " + i);
                    RRSIGRecord rr = (RRSIGRecord)r;
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
        }
        else{
            System.out.println("no records !");
        }
        printSecurityRating(message);
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

    private void checkAlgorithm(int number) {
        System.out.print("   Algorithm:   ");
        switch (number){
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

    private void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }
}



