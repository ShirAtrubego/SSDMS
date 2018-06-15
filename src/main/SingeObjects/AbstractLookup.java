package main.SingeObjects;

import org.xbill.DNS.*;

public interface AbstractLookup {

        void  setResolver(Resolver resolver);

        void setSearchPath(Name[] domains);

        void setSearchPath(String[] domains) throws TextParseException;

        void setCache(Cache cache);

        void setNdots(int ndots);

        void setCredibility(int credibility) ;

        Record[] run();

        Record[] getAnswers();


        Name[] getAliases();

        int getResult();

        String getErrorString();

    }

