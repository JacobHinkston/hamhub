package org.hamhub.common.frequency;

public enum FrequencyType {
    TX_RX("([0-9]){1,3}\\.([0-9]{2})(5|0)([0]{2})"),

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
     * Catches commmon UV5R Step:
     * 5.00
     */
    STEP("([0-9]{1,2}\\.[0]{2})"),

    /**
     * Catches common FT3D Step:
     * 5 kHz
     */
    STEP_UNIT("(([0-9]{1,3})(\\.[0])?)\s(kHz)"),

    /**
     * Catches all CTCSS Frequencies:
     * 100.0
     * 88.5
     * 123.5
     * 131.8
     */
    CTCSS("([0-9]{2,3})\\.([0-9]{1})");

    private String validator;

    private FrequencyType(String validator) {
        this.validator = validator;
    }

    public String getValidator() {
        return validator;
    }
}


