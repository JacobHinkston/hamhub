package org.hamhub.api.yeasu;

import java.util.ArrayList;
import java.util.List;

import org.hamhub.api.base.BaseCsv;
import org.hamhub.api.base.BaseRow;

public class FT3D extends BaseCsv {
    // TODO: Implement

    private static final int MEMORY_SIZE = 999;

    public FT3D(List<FT3DRow> rows) {
        
        super(rows);

        if (rows.size() > MEMORY_SIZE) {
            throw new IllegalStateException("FT3D Row size of " + rows.size() + " exceeds the maximum of " + MEMORY_SIZE);
        }

    }

    public static FT3D fromCsv(List<List<String>> csv) {

        List<FT3DRow> ft3dRows = new ArrayList<>();

        // Skip the last value of the CSV, it is empty. ( -1 )
        for (int i = 0; i < csv.size(); i++) {
            List<String> row = csv.get(i);

            // Remove the last value of the FT3D row, it is empty.
            row.remove(FT3DColumn.HEADER_SIZE);
           
            FT3DRow ft3dRow = new FT3DRow(row);
            int errIndex = ft3dRow.init();

            if (errIndex != -1) {
                System.err.println("There was an error parsing CSV at row: " + i + ", col: " + errIndex);
            } else {
                ft3dRows.add(ft3dRow);
            }
        }

        return new FT3D(ft3dRows);
    }

    

}
