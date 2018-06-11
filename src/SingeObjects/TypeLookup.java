package SingeObjects;

import org.xbill.DNS.*;

public class TypeLookup implements InterfaceLookup {


        private Lookup internalLookup;

        public TypeLookup (Lookup lookup){
            internalLookup = lookup;
        }
        /*
            public TypeLookup(String name, int type) throws TextParseException {
                this(TypeName.fromString(name), type, DClass.IN);
            }
        */
        @Override
        public void setResolver(Resolver resolver) {
            internalLookup.setResolver(resolver);
        }

        @Override
        public void setSearchPath(Name[] domains) {
            internalLookup.setSearchPath(domains);
        }

        @Override
        public void setSearchPath(String[] domains) throws TextParseException {
            internalLookup.setSearchPath(domains);
        }

        @Override
        public void setCache(Cache cache) {
            internalLookup.setCache(cache);
        }

        @Override
        public void setNdots(int ndots) {
            internalLookup.setNdots(ndots);
        }

        @Override
        public void setCredibility(int credibility) {
            internalLookup.setCredibility(credibility);
        }

        @Override
        public Record[] run() {
            return internalLookup.run();
        }

        @Override
        public Record[] getAnswers() {
            return internalLookup.getAnswers();
        }

        @Override
        public Name[] getAliases() {
            return internalLookup.getAliases();
        }

        @Override
        public int getResult() {
            return internalLookup.getResult();
        }

        @Override
        public String getErrorString() {
            return internalLookup.getErrorString();
        }
    }

