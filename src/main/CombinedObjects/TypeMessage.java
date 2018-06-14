package main.CombinedObjects;

import org.xbill.DNS.Message;
import org.xbill.DNS.Record;

public class TypeMessage implements InterfaceMessage {
    private Message internalMessage;

    TypeMessage(Message message) {
        internalMessage = message;
    }

    @Override
    public Record[] getSectionArray(int section) {
        return internalMessage.getSectionArray(section);
    }

    @Override
    public String toString() {
        return internalMessage.toString();
    }
}
