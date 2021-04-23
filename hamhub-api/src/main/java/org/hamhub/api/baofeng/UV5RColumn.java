package org.hamhub.api.baofeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UV5RColumn {

    INDEX(0, "Location", "", "[0-9]{1,3}"),
    NAME(1, "Name", "", "[a-zA-Z0-9]{1,7}"),
    RX(2, "Frequency", "145.00000", "([0-9]){1,3}\\.([0-9]{3})(5|0)([0]{2})"),
    DUPLEX(3, "Duplex", "", "(off|-|\\+)?"),
    OFFSET(4, "Offset", "0.000000", "(0|5{1})\\.(6|0)([0]{5})"),
    TONE_MODE(5, "Tone", "", "(Tone|DTCS)?"),
    TX_CTCSS(6, "rToneFreq", "88.5", "([0-9]{1,3})\\.([0-9]{1})"),
    RX_CTCSS(7, "cToneFreq", "88.5", "([0-9]{1,2})\\.([0-9]{1})"),
    DCS_CODE(8, "DtcsCode", "023", "[0-9]{3}"),
    DTCS_POLARITY(9, "DtcsPolarity", "NN", "(N|R)(N|R)"),
    MODE(10, "Mode", "FM", "(N?)(FM)"),
    STEP(11, "TStep", "5.00", ""),
    SKIP(12, "Skip", "", "S?"),
    COMMENT(13, "Comment", "", null),
    URCALL(14, "URCALL", "", null),
    RPT1CALL(15, "RPT1CALL", "", null),
    RPT2CALL(16, "RPT2CALL", "", null),
    DVCODE(17, "DVCODE", "", null);

    private static final Map<Integer, UV5RColumn> COLUMN_BY_INDEX = new HashMap<>();
    private static final Map<String, UV5RColumn> COLUMN_BY_LABEL = new HashMap<>();
    private static final Map<Integer, String> VALIDATOR_BY_INDEX = new HashMap<>();
    
    public static final int HEADER_SIZE = UV5RColumn.values().length;
    public static List<String> LABELS = new ArrayList<>();
    public static List<String> VALIDATORS = new ArrayList<>();

    static {
        for (UV5RColumn col : values()) {
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

    private UV5RColumn(int index, String label, String defaultValue, String validator) {
        this.index = index;
        this.label = label;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public int getIndex() { return index; }
    public String getLabel() { return label; }
    public String getDefaultValue() { return defaultValue; }
    public String getValidator() { return validator; }

    /**
     * Get the Column at an index.
     * @param index
     * @return
     */
    public static UV5RColumn getColumnAtIndex(int index) {
        return COLUMN_BY_INDEX.get(index);
    }

    /**
     * Get a Column by the label.
     * @param label
     * @return
     */
    public static UV5RColumn getColumnByLabel(String label) {
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

}
