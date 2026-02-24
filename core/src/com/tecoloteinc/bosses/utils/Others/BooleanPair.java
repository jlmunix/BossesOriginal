package com.tecoloteinc.bosses.utils.Others;

/**
 * This class holds all general types used in this
 * game.
 */

    /**
     * This class holds all general types used in this
     * game.
     */
    public class BooleanPair {

        boolean _a=false;
        boolean _b=false;
        /** Constructor */
        public BooleanPair(boolean a, boolean b) {
            _a = a;
            _b = b;
        }

        /** Copy constructor */
        public BooleanPair(BooleanPair bp) {
            this._a = bp._a;
            this._b = bp._b;
        }

        /**
         * @return returns true whether any of the stored values
         * is true.
         */
        public boolean anyTrue(){
            return _a || _b;
        }

        /**
         * With this method, the current booleans values
         * can be obtained and stored in another types.
         * @param a The parameter to fill with _a.
         * @param b The parameter to fill with _b.
         */
        public void getBooleans(boolean a, boolean b){
            a = _a;
            b = _b;
        }

        public boolean getA(){
            return _a;
        }
        public boolean getB(){
            return _b;
        }

        public int getIntA() { return _a ? 1 : -1; }
        public int getIntB() { return _b ? 1 : -1; }
    }


