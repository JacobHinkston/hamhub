package org.hamhub.common.frequency;

import java.util.List;

public class Ctcss extends Frequency {

    public static enum Type {
        /**
        * Catches all CTCSS Frequencies:
        * 100.0
        * 88.5
        * 123.5
        * 131.8
        */
        CTCSS("([0-9]{2,3})\\.([0-9]{1})");

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

    public Ctcss(String frequency) {
        this.frequency = frequency;
    }

}
