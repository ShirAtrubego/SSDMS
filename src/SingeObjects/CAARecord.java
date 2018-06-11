package SingeObjects;

import org.xbill.DNS.*;
//import testing.TestingResolver;

import java.io.IOException;

public class CAARecord {

    private Name name = null;
    private int type = 1;
    private int dclass = 1;


    public InterfaceMessage getCAARecord(String hostname) throws IOException {
        disableWarning();

        SimpleResolver res = new SimpleResolver();
        // TestingResolver resTest = new TestingResolver();

        name = Name.fromString(hostname, Name.root);
        type = 257;
        dclass = 1;
        Record caaKey = Record.newRecord(name, type, dclass);
        InterfaceMessage queryCAA = new TypeMessage(Message.newQuery(caaKey));

        //Message responseCAATest = res.send(queryCAA);
        InterfaceMessage responseCAA = new TypeMessage( res.send(queryCAA.toXbillMessage()) );

        return responseCAA;
    }

    public void printCAASections(InterfaceMessage message){
        System.out.println("-------------------------------------------------------CAA Query-------------------------------------------------------");
        Record[] sect1 = message.getSectionArray(1);if(null != sect1){
            for(int i = 0; i < sect1.length; i++){
                Record r = sect1[i];
                if(r instanceof org.xbill.DNS.CAARecord) {
                    System.out.println("CAARECORD    " + i);
                    org.xbill.DNS.CAARecord dr = (org.xbill.DNS.CAARecord) r;
                    System.out.println(i + "   Flag:  " + dr.getFlags() + "   :   Flag 0 is currently used to represent the critical flag, which isn't in use anymore.");
                    System.out.print(i + "   Tag:  " + dr.getTag());
                    String tag = dr.getTag();
                    checkTag(tag);
                    System.out.println(i + "   Value:  " + dr.getValue() + "   :   The value associated with the tag.");
                }
            }
        }
        else{
            System.out.println("no records !");
        }
    }

    private void checkTag(String tag) {
        switch(tag){
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

    private void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }
}

