package org.hamhub.api.baofeng;

import java.util.ArrayList;
import java.util.List;

import org.hamhub.api.base.BaseCsv;
public class UV5R extends BaseCsv {

    private static final int MEMORY_SIZE = 128;

    public UV5R(List<UV5RRow> rows) {
        super(rows);

        if (rows.size() > MEMORY_SIZE) {
            throw new IllegalStateException("UV5R row size of " + rows.size() + " exceeds the maximum of " + MEMORY_SIZE);
        }
    }

    public static UV5R fromCsv(List<List<String>> csv) {

        List<UV5RRow> uv5rRows = new ArrayList<>();

        for (int i = 0; i < csv.size(); i++) {
            List<String> row = csv.get(i);
           
            UV5RRow uv5rRow = new UV5RRow(row);
            int errIndex = uv5rRow.init();

            if (errIndex != -1) {
                System.err.println("There was an error parsing CSV at row: " + i + ", col: " + errIndex);
            } else {
                uv5rRows.add(uv5rRow);
            }
        }

        return new UV5R(uv5rRows);
    }
}
