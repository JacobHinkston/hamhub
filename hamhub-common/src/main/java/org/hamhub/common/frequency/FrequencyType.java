package org.hamhub.common.frequency;

import java.util.List;

public enum FrequencyType {
    /**
    * Catches all CTCSS Frequencies:
    * 100.0
    * 88.5
    * 123.5
    * 131.8
    */
    CTCSS("([0-9]{2,3})\\.([0-9]{1})"),

    /**
     * Catches common UV5R Offset.
     * 5.000000
     */
    OFFSET("(([0-9]{1})|())\\.([0-9]{6})"),

    /**
     * Catches common FT3D offset
     * 5 MHz
     */
    OFFSET_UNIT("([0-9]{1,3}\skHz)|(([0-9]{1})\\.([0]{5})\sMHz)"),

    /**
     * Catches common UV5R Step:
     * 5.00
     */
    STEP("([0-9]{1,2})\\.[0]{2})"),

    /**
     * Catches common FT3D Step:
     * 5 kHz
     */
    STEP_UNIT("(([0-9]{1,3})(\\.[0-9]{1})?)\s(kHz)"),

    /**
     * Only acceptable TX_RX frequency
     * 146.64000
     * 50.00000
     */
    TX_RX("([0-9]{1,3})\\.([0-9]{2}(5|0)[0]{2})"),

    /**
     * Default, emtpty frequency type
     */
    NONE(null);


    public static List<String> VALIDATORS;
    static {
        for (FrequencyType type: values()) {
            VALIDATORS.add(type.getValidator());
        }
    }

    private String validator;

    private FrequencyType(String validator) {
        this.validator = validator;
    }

    /**
     * Returns the FrequencyType regex
     * @return
     */
    public String getValidator() {
        return validator;
    }
}
