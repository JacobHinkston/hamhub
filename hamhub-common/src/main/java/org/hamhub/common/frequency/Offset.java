package org.hamhub.common.frequency;

import java.util.List;

public class Offset extends Frequency {

    public static enum Type {
        /**
         * Catches common UV5R Offset.
         * 5.000000
         */
        OFFSET("(([0-9]{1})|())\\.([0-9]{6})"),

        /**
         * Catches common FT3D offset
         * 5 MHz
         */
        OFFSET_UNIT("([0-9]{1,3}\skHz)|(([0-9]{1})\\.([0]{5})\sMHz)");

        public static List<String> VALIDATORS;
        static {
            for (Type type: values()) {
                VALIDATORS.add(type.getValidator());
            }
        }

        private String validator;
        private Type(String validator) { this.validator = validator; }
        public String getValidator() { return validator; }
    }

    public Offset(String frequency) {
        this.frequency = frequency;  
    }   
}
