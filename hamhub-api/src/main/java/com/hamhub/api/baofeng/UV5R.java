package com.hamhub.api.baofeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UV5R {

    public static String FORMAT = "UV5R";

    public static enum Column {
        INDEX(0, "Location"),
        NAME(1, "Name"),
        RX(2,"Frequency"),
        DUPLEX(3, "Duplex"),
        OFFSET(4, "Offset"),
        CTCSS_TONE(5, "Tone"),
        TONE_SQUELCH(6, "rToneFreq"),
        C_TONE_FREQ(7, "cToneFreq"),
        DCS_CODE(8, "DtcsCode"),
        DTCS_POLARITY(9, "DtcsPolarity"),
        MODE(10, "Mode"),
        STEP(11, "TStep"),
        SKIP(12, "Skip"),
        COMMENT(13, "Comment"),
        URCALL(14, "URCALL"),
        RPT1CALL(15, "RPT1CALL"),
        RPT2CALL(16, "RPT2CALL"),
        DVCODE(17, "DVCODE");

        private final int index;
        private final String label;

        private static final Map<Integer, Column> BY_INDEX = new HashMap<>();
        private static final Map<String, Column> BY_LABEL = new HashMap<>();

        static {
            for (Column col : values()) {
                BY_INDEX.put(col.index, col);
                BY_LABEL.put(col.label, col);
            }
        }

        private Column(int index, String label) {
            this.index = index;
            this.label = label;
        }

        public int getIndex() {
            return this.index;
        }

        public String getLabel() {
            return this.label;
        }

        /**
         * Return the header Column size.
         * @return
         */
        public static int getHeaderSize() {
            return Column.values().length;
        }

        /**
         * Get the Column at an index.
         * @param index
         * @return
         */
        public Column getColumnAtIndex(int index) {
            return BY_INDEX.get(index);
        }

        /**
         * Get a Column by the label.
         * @param label
         * @return
         */
        public Column getColumnByLabel(String label) {
            return BY_LABEL.get(label);
        }
    }

    /**
     * List, maintaining maps of Sring/String values
     */
    private List<UV5RRow> rows = new ArrayList<>();

    public UV5R() {
        // TODO: IMPLEMENT
    }

    public List<UV5RRow> getColumns() {
        return rows;
    }

    public void setColumns(List<UV5RRow> rows) {
        this.rows = rows;
    }

    /**
     * Take in a String[] of csv columns, and verify they match the header enums.
     * @param csv
     * @return
     */
    public static boolean verifyHeader(List<String> csv) {
        // TODO: Abstract into a parent method.
        final Column[] columns = Column.values();

        for (int i = 0; i < csv.size(); i++) {
            if (!csv.get(i).equals(columns[i].getLabel())) {
                return false;
            } 
        }
        return true;
    }
}
