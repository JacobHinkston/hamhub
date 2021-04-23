package org.hamhub.common.frequency;

import java.util.List;

public class Step extends Frequency {

    public static enum Type {
        /**
         * Catches common UV5R Step:
         * 5.00
         */
        STEP("([0-9]{1,2})\\.[0]{2})"),

        /**
         * Catches common FT3D Step:
         * 5 kHz
         */
        STEP_UNIT("(([0-9]{1,3})(\\.[0-9]{1})?)\s(kHz)");

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

    public Step(String frequency) {
        this.frequency = frequency;
    }



}
