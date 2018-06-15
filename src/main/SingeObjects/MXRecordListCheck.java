package main.SingeObjects;

import org.xbill.DNS.*;

public class MXRecordListCheck {

    public String checkMXRecordList(String name) throws TextParseException {
        String [] s = new String[2];
        Record[] records = new Lookup(name, Type.MX).run();
        for (int i = 0; i < records.length; i++) {
            MXRecord mx = (MXRecord) records[i];
            // System.out.println("Host " + mx.getTarget() + " has preference " + mx.getPriority());
//            s [i]= (mx.getTarget());
            return mx.getTarget().toString();
        }
        //       System.out.println("records: " + records);
        return null;
    }
}
