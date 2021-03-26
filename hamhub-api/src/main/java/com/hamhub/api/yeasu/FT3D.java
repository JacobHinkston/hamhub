package com.hamhub.api.yeasu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FT3D {

    public static String FORMAT = "FT3D";

    public static enum Column {
        INDEX(0, "", ""),
        RX(1, "Receive Frequency", "145.000"),
        TX(2, "Transmit Frequency", "145.000"),
        OFFSET(3, "Offset Frequency", ""),
        OFFSET_DIR(4, "Offset Direction", "Simplex"),
        MODE(5, "Operating Mode", "FM"),
        AUTO(6, "AMS", "N"),
        NAME(7, "Name", ""),
        TONE_MODE(8, "Tone Mode","Tone"),
        CTCSS_TONE(9, "CTCSS", ""),
        DCS_CODE(10, "DCS", "023"),
        DCS_POL(11, "DCS Polarity", "023"), 
        RX_DGID(12, "Rx DGID", "0"),
        TX_DGID(13, "Tx DGID", "0"),
        USER_CTCSS(14, "User CTCSS", "0"),
        TX_POWER(15, "Tx Power", "High"),
        SKIP(16, "Skip", "Scan"),
        STEP(17, "Step", "5 kHz"),
        ATTENUATOR(18, "Attenuator", "N"),
        SQUELCH(19, "SMeter Squelch", "0"),
        BELL(20, "Bell", "0"),
        HALF_DEVIATION(21, "Half Deviation", "N"),
        CLOCK_SHIFT(22, "Clock Shift", "N"),
        B1(23, "Bank 1", "N"),
        B2(24, "Bank 2", "N"),
        B3(25, "Bank 3", "N"),
        B4(26, "Bank 4", "N"),
        B5(27, "Bank 5", "N"),
        B6(28, "Bank 6", "N"),
        B7(29, "Bank 7", "N"),
        B8(30, "Bank 8", "N"),
        B9(31, "Bank 9", "N"),
        B10(32, "Bank 10", "N"),
        B11(33, "Bank 11", "N"),
        B12(34, "Bank 12", "N"),
        B13(35, "Bank 13", "N"),
        B14(36, "Bank 14", "N"),
        B15(37, "Bank 15", "N"),
        B16(38, "Bank 16", "N"),
        B17(39, "Bank 17", "N"),
        B18(40, "Bank 18", "N"),
        B19(41, "Bank 19", "N"),
        B20(42, "Bank 20", "N"),
        B21(43, "Bank 21", "N"),
        B22(44, "Bank 22", "N"),
        B23(45, "Bank 23", "N"),
        B24(46, "Bank 24", "N"),
        COMMENT(47, "Comment", "");

        private final int index;
        private final String label;
        private final String defaultVal;

        private static final Map<Integer, Column> BY_INDEX = new HashMap<>();
        private static final Map<String, Column> BY_LABEL = new HashMap<>();

        static {
            for (Column col : values()) {
                BY_INDEX.put(col.index, col);
                BY_LABEL.put(col.label, col);
            }
        }
        
        /**
         * Primary constructor.
         * @param pos
         * @param colName
         */
        private Column(int index, String label, String defaultVal) { 
            this.index = index;
            this.label = label;
            this.defaultVal = defaultVal;
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
        public static Column getColumnAtIndex(int index) {
            return BY_INDEX.get(index);
        }

        /**
         * Get a Column by the label.
         * @param label
         * @return
         */
        public static Column getColumnByLabel(String label) {
            return BY_LABEL.get(label);
        }

        /**
         * @UNUSED
         * Return a List of column labels, 
         * @return
         */
        public List<String> getColumnLabels() {
            List<String> columnLabels = new ArrayList<>();
            for (Column column : values()) {
                columnLabels.add(column.getLabel());
            }
            return columnLabels;
        }

        public String getDefaultVal() {
            return defaultVal;
        }
    }

    /**
     * List, maintaining maps of Sring/String values
     */
    private List<FT3DRow> rows = new ArrayList<>();


    public FT3D(List<FT3DRow> rows) {
        this.rows = rows;
    }

    /**
     * Sets List<FT3D> rows.
     * @return
     */
    public List<FT3DRow> getRows() {
        return rows;
    }

    /**
     * Returns List<FT3D> rows.
     * @param rows
     */
    public void setRows(List<FT3DRow> rows) {
        this.rows = rows;
    }
    
    /**
     * @param csv The string[] made up of a csv column 
     * @return
     * TODO: Make this return an int for the index of failure, or -1.
     */
    public static boolean verifyHeader(List<String> csv) {
        final Column[] columns = Column.values();

        // Size -1 because we don't care about the last value, it's empty.
        // i = 0 because there is an empty header value in RT Systems headers
        for (int i = 1; i < csv.size() - 1; i++) {
            // Offset (+1) to account for 'INDEX', which is empty in the header.
            String columnVal = columns[i].getLabel(); 
            String csvVal = csv.get(i);
            if (!csvVal.equals(columnVal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * TESTING ONLY.
     * @UNUSED
     */
    public void printRowColumnValues() {
        System.out.println("--------------------------FT3D ROWS--------------------------");
        for (FT3DRow row : getRows()) {
            Map<String, String> rowMap = row.getColumnValues();

            for(String value : rowMap.values()) {
                System.out.print(value + "|");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * 
     */
    public List<String> getColumnByName(String name) {
        List<String> rowNames = new ArrayList<String>();
        for(FT3DRow row: rows) {
            rowNames.add(row.getColumnValueByName(FT3D.Column.NAME.getLabel()) + ":" + row.getColumnValueByName(name));
        }
        return rowNames;
    }
}
