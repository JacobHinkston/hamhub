package org.hamhub.ccsv;

import org.hamhub.ccsv.common.CsvReader;
import org.hamhub.api.Format;
import org.hamhub.api.baofeng.UV5R;
import org.hamhub.api.base.BaseCsv;
import org.hamhub.api.yeasu.FT3D;

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

    List<List<String>> csv;

    Format format;

    public void run(String filePath, String exportFormat) throws FileNotFoundException {

        File file = new File(filePath);
        csvReader = new CsvReader(file);

        this.csv = csvReader.getCsv();
        this.format = csvReader.getFormat();

        BaseCsv baseCsv = createBaseCsv(csv, format);

        if (baseCsv != null) {
            baseCsv.printCsvParsed();
        } else {
            return;
        }

    }

    private BaseCsv createBaseCsv(List<List<String>> csv, Format format) {
        BaseCsv baseCsv = null;
        
        switch (format) {
            case FT3D:
                baseCsv = FT3D.fromCsv(csv);
                break;
            case UV5R:
                baseCsv = UV5R.fromCsv(csv);
                break;
            case NONE:
                System.err.println("CcsvRunner::createBaseCsv, ERROR - No format detected for CSV.");
                break;
            default:
                System.err.println("CcsvRunner::createBaseCsv, ERROR - Format not supported.");

        }
        return baseCsv;
    }
}
