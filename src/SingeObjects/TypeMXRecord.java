package SingeObjects;

import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Name;

public class TypeMXRecord implements InterfaceMXRecord{

        private MXRecord internalMxRecord;

        public TypeMXRecord (MXRecord mxRecord){
            internalMxRecord = mxRecord;
        }


        @Override
        public Name getTarget() {
            return internalMxRecord.getTarget();
        }

        @Override
        public int getPriority() {
            return internalMxRecord.getPriority();
        }


        @Override
        public Name getAdditionalName() {
            return internalMxRecord.getAdditionalName();
        }
    }

