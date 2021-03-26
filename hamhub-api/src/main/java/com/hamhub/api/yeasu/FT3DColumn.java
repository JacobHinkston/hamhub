package com.hamhub.api.yeasu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FT3DColumn {

    INDEX(0, "", "", ""),
    RX(1, "Receive Frequency", "145.00000", "([0-9]){1,3}\\.([0-9]{2})(5|0)([0]{2})"),
    TX(2, "Transmit Frequency", "145.00000", "([0-9]){1,3}\\.([0-9]{2})(5|0)([0]{2})"),
    OFFSET(3, "Offset Frequency", null, "(([5-6]{1}[0]{2})\s[kHz])|([1-9]{1}\\.[0]{5}\sMHz)"),
    DUPLEX(4, "Offset Direction", "Simplex", "Simplex|Minus|Split|Plus"),
    MODE(5, "Operating Mode", "FM", "FM|AM|DN|VM"),
    AUTO(6, "AMS", "N", "N|Y"),
    NAME(7, "Name", "", null),
    TONE_MODE(8, "Tone Mode", "None", "None|Tone|T Sql|DCS|Rev CTCSS|User CTCSS|Pager|D Code|T DCS|D Tone"),
    TX_CTCSS(9, "CTCSS", "67.0", "([0-9]{2,3})\\.([0-9]{1})"),
    DCS_CODE(10, "DCS", "023", "[0-9]{3}"),
    DTCS_POLARITY(11, "DCS Polarity", "Both N", "[0-9]{3}"), 
    RX_DGID(12, "Rx DGID", "0", "[0-9]{1,2}"),
    TX_DGID(13, "Tx DGID", "0", "[0-9]{1,2}"),
    RX_CTCSS(14, "User CTCSS", "0", "[0-1]{1}"),
    TX_POWER(15, "Tx Power", "High", "Low|Low1|Low2|Low3|High"),
    SKIP(16, "Skip", "Scan", "Skip|Scan|P Scan"),
    STEP(17, "Step", "5 kHz", "((([0-9]{1,3}\\.[2-5]{1,2})|([0-9]{1,3}))\skHz)|Auto"),
    ATTENUATOR(18, "Attenuator", "N", "N|Y"),
    SQUELCH(19, "SMeter Squelch", "0", "[0-1]{1}"),
    BELL(20, "Bell", "0", "[0-1]{1}"),
    HALF_DEVIATION(21, "Half Deviation", "N", "N|Y"),
    CLOCK_SHIFT(22, "Clock Shift", "N", "N|Y"),
    B1(23, "Bank 1", "N", "N|Y"),
    B2(24, "Bank 2", "N", "N|Y"),
    B3(25, "Bank 3", "N", "N|Y"),
    B4(26, "Bank 4", "N", "N|Y"),
    B5(27, "Bank 5", "N", "N|Y"),
    B6(28, "Bank 6", "N", "N|Y"),
    B7(29, "Bank 7", "N", "N|Y"),
    B8(30, "Bank 8", "N", "N|Y"),
    B9(31, "Bank 9", "N", "N|Y"),
    B10(32, "Bank 10", "N", "N|Y"),
    B11(33, "Bank 11", "N", "N|Y"),
    B12(34, "Bank 12", "N", "N|Y"),
    B13(35, "Bank 13", "N", "N|Y"),
    B14(36, "Bank 14", "N", "N|Y"),
    B15(37, "Bank 15", "N", "N|Y"),
    B16(38, "Bank 16", "N", "N|Y"),
    B17(39, "Bank 17", "N", "N|Y"),
    B18(40, "Bank 18", "N", "N|Y"),
    B19(41, "Bank 19", "N", "N|Y"),
    B20(42, "Bank 20", "N", "N|Y"),
    B21(43, "Bank 21", "N", "N|Y"),
    B22(44, "Bank 22", "N", "N|Y"),
    B23(45, "Bank 23", "N", "N|Y"),
    B24(46, "Bank 24", "N", "N|Y"),
    COMMENT(47, "Comment", "", null);

    private static final Map<Integer, FT3DColumn> COLUMN_BY_INDEX = new HashMap<>();
    private static final Map<String, FT3DColumn> COLUMN_BY_LABEL = new HashMap<>();
    private static final Map<Integer, String> VALIDATOR_BY_INDEX = new HashMap<>();

    public static int HEADER_SIZE = FT3DColumn.values().length;
    public static List<String> LABELS = new ArrayList<>();
    public static List<String> VALIDATORS = new ArrayList<>();

    static {
        for (FT3DColumn col : values()) {
            COLUMN_BY_INDEX.put(col.index, col);
            COLUMN_BY_LABEL.put(col.label, col);
            VALIDATOR_BY_INDEX.put(col.index, col.validator);
            LABELS.add(col.getLabel());
            VALIDATORS.add(col.getValidator());
        }
    }
    
    private final int index;
    private final String label;
    private final String defaultValue;
    private final String validator;

    private FT3DColumn(int index, String label, String defaultValue, String validator) { 
        this.index = index;
        this.label = label;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public int getIndex() { return index; }
    public String getLabel() { return label; }
    public String getDefaultVal() { return defaultValue; }
    public String getValidator() { return validator; }
    
    /**
     * Get the Column at an index.
     * @param index
     * @return
     */
    public static FT3DColumn getColumnAtIndex(int index) {
        return COLUMN_BY_INDEX.get(index);
    }

    /**
     * Get a Column by the label.
     * @param label
     * @return
     */
    public static FT3DColumn getColumnByLabel(String label) {
        return COLUMN_BY_LABEL.get(label);
    }

    /**
     * Get the Validator by index.
     * @param index
     * @return
     */
    public static String getValidatorAtIndex(int index) {
        return VALIDATOR_BY_INDEX.get(index);
    }

    /**
     * Get a map of validators by index.
     * @return
     */
    public static Map<Integer, String> getValidatorsByIndex() {
        return VALIDATOR_BY_INDEX;
    }
}
