package main.CombinedObjects;

import org.xbill.DNS.*;

public class TypeMessage implements InterfaceMessage {
    private Message internalMessage;

    TypeMessage(Message message){
        internalMessage = message;
    }

    @Override
    public void setHeader(Header h) {
        internalMessage.setHeader(h);
    }

    @Override
    public Header getHeader() {
        return internalMessage.getHeader();
    }

    @Override
    public void addRecord(Record r, int section) {
        internalMessage.addRecord(r, section);
    }

    @Override
    public boolean removeRecord(Record r, int section) {
        return internalMessage.removeRecord(r,section);
    }

    @Override
    public void removeAllRecords(int section) {
        internalMessage.removeAllRecords(section);
    }

    @Override
    public boolean findRecord(Record r, int section) {
        return internalMessage.findRecord(r,section);
    }

    @Override
    public boolean findRecord(Record r) {
        return internalMessage.findRecord(r);
    }

    @Override
    public boolean findRRset(Name name, int type, int section) {
        return internalMessage.findRRset(name,type,section);
    }

    @Override
    public boolean findRRset(Name name, int type) {
        return internalMessage.findRRset(name,type);
    }

    @Override
    public Record getQuestion() {
        return internalMessage.getQuestion();
    }

    @Override
    public int getRcode() {
        return internalMessage.getRcode();
    }

    @Override
    public Record[] getSectionArray(int section) {
        return internalMessage.getSectionArray(section);
    }

    @Override
    public RRset[] getSectionRRsets(int section) {
        return internalMessage.getSectionRRsets(section);
    }

    @Override
    public int numBytes() {
        return internalMessage.numBytes();
    }

    @Override
    public String sectionToString(int i) {
        return internalMessage.sectionToString(i);
    }

    @Override
    public Message toXbillMessage() {
        return internalMessage;
    }

    @Override
    public String toString(){
        return internalMessage.toString();
    }
}
