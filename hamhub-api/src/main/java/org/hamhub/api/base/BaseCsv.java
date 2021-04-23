package org.hamhub.api.base;

import java.util.List;

abstract public class BaseCsv {

    protected List<? extends BaseRow> rows;

    protected static int MEMORY_SIZE;

    public BaseCsv(List<? extends BaseRow> rows) {
        
        this.rows = rows;
    }

    public List<? extends BaseRow> getRows() {
        return this.rows;
    }

    public void printCsvParsed() {

        System.out.println();

        if (rows.size() > 0) {

            rows.forEach(row -> {
                row.printRow();
            });

        } else {
            System.err.println("BaseCsv::printRows, INFO - Nothing to print, rows are empty.");
        }

        System.out.println();
    }

}
