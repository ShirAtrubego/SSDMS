package main.CombinedObjects;

import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;

public interface InterfaceName {
    Name root = null;

    static Name
    fromString(String s, Name origin) throws TextParseException {
        if (s.equals("@") && origin != null)
            return origin;
        else if (s.equals("."))
            return (root);

        return new Name(s, origin);
    }

    short length();

    int labels();
}



