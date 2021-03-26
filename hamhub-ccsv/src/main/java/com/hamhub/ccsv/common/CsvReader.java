package com.hamhub.ccsv.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// import com.hamhub.api.BaseCsv;
import com.hamhub.api.baofeng.UV5R;
import com.hamhub.api.yeasu.FT3D;
import com.hamhub.api.yeasu.FT3DRow;

import java.io.File;
import java.io.FileNotFoundException;

public class CsvReader {

    /**
     * Enum containing the supported format types for CSVs.
     */
    public static enum Format {
        FT3D,
        UV5R,
        NONE
    }

    /**
     * File that maintains the requested parse.
     */
    private File file;

    /**
     * Format to convert to, once read.
     */
    private String exportFormat;

    /**
     * Format detected from read.
     */
    private Format format = Format.NONE;

    /**
     * 
     */
    private Object csvObject;
    
    /**
     * 
     * @param csvFile
     */
    public CsvReader(File file, String exportFormat) {
        this.file = file;
        this.exportFormat = exportFormat;
        init(file);
    }

    /**
     * Initialize the read of the csv file. 
     * @param csvFile
     */
    private void init(File csvFile) {

        try {
            Scanner scanner = new Scanner(csvFile);

            // Read the header first to detect a CSV type.
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> header = readLine(line);
                this.format = detectHeader(header);

                // If there was a detected format, handoff the scanner to a helper method.
                switch (this.format) {
                    case FT3D: {
                        System.out.println("INFO: Format Detected, FT3D");
                        // Hand off the Scanner to the hydrate method.
                        List<FT3DRow> ft3dRows = hydrateFT3DdRows(scanner);
                        FT3D ft3d = new FT3D(ft3dRows);
                        this.csvObject = ft3d;
                        break;
                    } case UV5R: {
                        // TODO: Implement.
                        System.out.println("INFO: Format Detected, UV5R");
                        break;
                    } case NONE: {
                        // TODO: Implement.
                        System.out.println("INFO: Format Detected, NONE");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File, " +  this.file.getPath() + ", could not be found.");
        }
    }

    /**
     * Verify that the headers match what is expected from any format.
     * @param csv
     * @return
     */
    public Format detectHeader(List<String> csv) {

        // In all FT3D headers, there is an empty header value for the INDEX.
        // We should omit this count (-1) since it will not be read in.
        int ft3dHeaderSize = FT3D.Column.getHeaderSize() + 1;
        // int uv5rHeaderSize = UV5R.Column.getHeaderSize();

        

        if (csv.size() == ft3dHeaderSize && FT3D.verifyHeader(csv)) {
            return Format.FT3D;
        // } else if (csv.size() == uv5rHeaderSize && UV5R.verifyHeader(csv)) {
        //     return Format.UV5R;
        } else {
            System.out.println("There was a problem detecting the header:\n" +
                "Csv Header Length: " + csv.size() + "\n" + 
                "FT3D Header Length: " + ft3dHeaderSize + "\n" + 
                // "UV5R Header Length: " + uv5rHeaderSize + "\n" + 
                "");
            return Format.NONE;
        }
    }

    /**
     * Detect if a column is bad. Useful for ending FT3DColumn hydration.
     * @param column
     */
    private static boolean detectBadColumn(List<String> row) {
        final int tolerance = 2;
        int count = 0;

        for (String value : row) {
            if (value.equals("")) {
                count ++;
            } 

            if (count >= tolerance) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads in a single csv line, and returns a list of Strings.
     * @param csv
     * @param persist
     */
    private static List<String> readLine(String line) {
        /**
         * Learning lesson: some of these CSV files contain custom values: comments.
         * In these comments all UTF-8 characters are allowed, including more commas.
         * These commas force the programming software to append quotes around the value in the table.
         * 
         * Solution: Catch if the next part starts with Quotes or not, if it does, reset the break index.
         * Side Note: The length part here is required, sometimes the comment is at the end. 
         * The reference to charAt will throw an exception.
         */
        List<String> columns = new ArrayList<String>();
		
		int breakIndex = line.indexOf(",");
		String nextPart = line;
		
		boolean finished = false;
		while (!finished) {

			String value = null;

			if (breakIndex == -1) {
                value = nextPart;
				finished = true;
			} else {
                value = nextPart.substring(0, breakIndex);
			}

            /**
             * This is an important edge case. 
             * RT Systems decided there needed to be a comma at the end
             * of the CSV without a value ? Catch this and throw it away.
             * 
             * TODO: Will likely interfere with UV5R Column reading, since there are no default value.
             */
             
            columns.add(value);
            
			nextPart = nextPart.substring(breakIndex + 1);
            
			if (nextPart.length() > 0 && nextPart.charAt(0) == '\"') {
				breakIndex = nextPart.indexOf("\",") + 1;
			} else {
				breakIndex = nextPart.indexOf(",");
			}
		}
		return columns;
    }

    private List<FT3DRow> hydrateFT3DdRows(Scanner scanner) {
        List<FT3DRow> ft3dRows = new ArrayList<>();

        for (int i = 0; scanner.hasNextLine(); i++) {
            String line = scanner.nextLine();
            
            List<String> row = readLine(line);
            row.remove(48); // Remove random empty value at the end of the list.

            if (!detectBadColumn(row)) {
                // FT3D Formatting is weird, it populates the entire CSV with all 999 positions, even if they are empty.
                FT3DRow ft3dRow = new FT3DRow(row);
                try { 
                    ft3dRow.init();
                    ft3dRows.add(ft3dRow);
                } catch (Exception e) {
                    System.out.println("Parsing error at Row: " + i + "\n" + e);
                }
            } else {
                // We have reached the end of the CSV. Return what we have.
                return ft3dRows;
            }
        }

        return ft3dRows;
        
    }

    private void hydrateUV5RColumns(Scanner scanner) {
        // TODO: Implement.
    }


    public Object export() {
        return this.csvObject;
    }
}
