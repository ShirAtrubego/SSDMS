package main.CombinedObjects;

import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;

public class TypeName implements InterfaceName {

    private Name internalName;

    public static Name
    fromString(String s, Name origin) throws TextParseException {
        if (s.equals("@") && origin != null)
            return origin;
        else if (s.equals("."))
            return (root);

        return new Name(s, origin);
    }

    public TypeName(Name name) {
        internalName = name;
    }


    @Override
    public short length() {
        return internalName.length();
    }

    @Override
    public int labels() {
        return internalName.labels();
    }
}

