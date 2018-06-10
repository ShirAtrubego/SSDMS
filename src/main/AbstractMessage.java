package main;

import org.xbill.DNS.*;

public interface AbstractMessage {

    /**
     * Replaces the Header with a new one.
     * @see Header
     */
    void setHeader(Header h);

    /**
     * Retrieves the Header.
     * @see Header
     */
    Header getHeader();


    /**
     * Adds a record to a section of the Message, and adjusts the header.
     * @see Record
     * @see Section
     */
    void addRecord(Record r, int section);

    /**
     * Removes a record from a section of the Message, and adjusts the header.
     * @see Record
     * @see Section
     */
    boolean removeRecord(Record r, int section);

    /**
     * Removes all records from a section of the Message, and adjusts the header.
     * @see Record
     * @see Section
     */
    void removeAllRecords(int section);

    /**
     * Determines if the given record is already present in the given section.
     * @see Record
     * @see Section
     */
    boolean findRecord(Record r, int section);

    /**
     * Determines if the given record is already present in any section.
     * @see Record
     * @see Section
     */
    boolean findRecord(Record r);

    /**
     * Determines if an RRset with the given name and type is already
     * present in the given section.
     * @see RRset
     * @see Section
     */
    boolean findRRset(Name name, int type, int section);

    /**
     * Determines if an RRset with the given name and type is already
     * present in any section.
     * @see RRset
     * @see Section
     */
    boolean findRRset(Name name, int type);

    /**
     * Returns the first record in the QUESTION section.
     * @see Record
     * @see Section
     */
    Record getQuestion();

    /**
     * Returns the message's rcode (error code).  This incorporates the EDNS
     * extended rcode.
     */
    int getRcode();

    /**
     * Returns an array containing all records in the given section, or an
     * empty array if the section is empty.
     * @see Record
     * @see Section
     */
    Record [] getSectionArray(int section);

    /**
     * Returns an array containing all records in the given section grouped into
     * RRsets.
     * @see RRset
     * @see Section
     */
    RRset [] getSectionRRsets(int section);


    /**
     * Returns the size of the message.  Only valid if the message has been
     * converted to or from wire format.
     */
    int numBytes();

    /**
     * Converts the given section of the Message to a String.
     * @see Section
     */
    String sectionToString(int i);

    Message toXbillMessage();

}
