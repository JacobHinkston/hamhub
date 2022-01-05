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
    CTCSS("[0-9]{2,3}\\.[0-9]{1}", 0, -1),

    /**
     * Catches common UV5R Offset.
     * 5.000000
     * 0.600000
     */
    OFFSET("(([1-9]{1}\\.[0]{1})|([0]{1}\\.[1-9]{1}))([0]{5})", 0, -1),

    /**
     * Catches common FT3D offset
     * 5.00000 kHz
     * 600 MHz
     */
    OFFSET_UNIT("(([1-9]{1})\\.([0]{5})|([0-9]{3}))(\s)(M|k)Hz", 1, 6),

    /**
     * Catches common UV5R Step:
     * 5.00
     */
    STEP("([0-9]{1,2})\\.[0]{2})"),

    /**
     * Catches common FT3D Step:
     * 5 kHz
     * 25 kHz
     * 12.5 kHz
     */
    STEP_UNIT("(([0-9]{1,2})|([0-9]{1,2}\\.[0-9]{1,2}))\s(kHz)", 1, 4),

    /**
     * Only acceptable TX_RX frequency
     * 146.64000
     * 50.00000
     */
    TX_RX("([0-9]{1,3})\\.([0-9]{2}(5|0)[0]{2})"),

    /**
     * Default, empty frequency type
     */
    NONE(null);


    public static List<String> VALIDATORS;
    static {
        for (FrequencyType type: values()) {
            VALIDATORS.add(type.getValidator());
        }
    }

    private String validator;
    private int valueGroup;
    private int unitGroup;

    private FrequencyType(String validator, int valueGroup, int unitGroup) {
        this.validator = validator;
        this.valueGroup = valueGroup;
        this.unitGroup = unitGroup;
    }

    /**
     * Returns the FrequencyType regex
     * @return
     */
    public String getValidator() {
        return validator;
    }
}
