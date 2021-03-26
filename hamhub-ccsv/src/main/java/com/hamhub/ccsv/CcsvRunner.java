package com.hamhub.ccsv;

import com.hamhub.api.yeasu.FT3D;
import com.hamhub.ccsv.common.CsvReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * CLASS IS FOR TESTING ONLY.
 * 
 * The plan is to move this into a common library, and abstract it into a request.
 */
public class CcsvRunner {

    CsvReader csvReader;

    public void run(String filePath, String exportFormat) throws FileNotFoundException {

        File file = new File(filePath);
        csvReader = new CsvReader(file, "");
        FT3D ft3d = (FT3D) csvReader.export();
        List<String> ft3dRowsByName = ft3d.getColumnByName(FT3D.Column.COMMENT.getLabel());
        for(String name: ft3dRowsByName) {
            System.out.println(name);
        }
        ft3d.printRowColumnValues();

    }
}
