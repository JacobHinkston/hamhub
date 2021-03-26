package com.hamhub.api.yeasu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hamhub.api.BaseColumn;
import com.hamhub.api.yeasu.FT3D.Column;

public class FT3DRow extends BaseColumn {

    /**
     * Column values for an FT3D, mapped by String <FT3D.Column.label> to the raw value;
     * This is janky as hell, and needs to use Objects instead. That will happen later.
     * TODO: Use Objects for in-line types.
     */
    Map<String, String> columnValues = new HashMap<>();

    /**
     * String[] of columns used to create the column.
     */
    private List<String> columns;

    /**
     * int that stores the header column counts.
     */
    private static final int FT3D_HEADER_SIZE = FT3D.Column.getHeaderSize();

    /**
     * Primary constructor
     * @param columns
     */
    public FT3DRow(List<String> columns) {
        this.columns = columns;
    }

    /**
     * Initialize the column given a String[] of columns.
     * @param columns
     * @return
     * TODO: Learn how to do better error handling xD.
     */
    public void init() throws Exception {

        int columnError = -1;
        for (int i = 0; i < columns.size(); i++) {

            String columnValue = columns.get(i); 
            Column columnByIndex = Column.getColumnAtIndex(i);

            columnError = verifyColumn(columnValue, i);
            if (columnError == -1) {
                
                // This is stupid, prob should find a better key to use in this map? :thinking:
                // TODO: Figure out how to write code. :feels-pain:
                columnValues.put(columnByIndex.getLabel(), columnValue);
            } else {
                // If a column error was found, report it.
                throw new Exception("Column:" + i + ". VALUE=" + columnValue);
            
            }
        }

    }

    /**
     * Return the index of a column error.
     * @param columnValue
     * @param column
     * @return
     */
    private int verifyColumn(String rowValue, int column) {
        // TODO: IMPLEMENT
        return -1;
    }

    public Map<String, String> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Map<String, String> columnValues) {
        this.columnValues = columnValues;
    }

    public List<String> getColumns() {
        return columns;
    }

    public String getColumnValueByName(String name) {
        return this.columnValues.get(name);
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }


}
