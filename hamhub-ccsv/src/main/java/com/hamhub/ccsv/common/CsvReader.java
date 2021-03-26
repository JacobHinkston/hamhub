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
import com.hamhub.api.Format;
import com.hamhub.ccsv.utils.Validator;

import java.io.File;
import java.io.FileNotFoundException;

public class CsvReader {

    /**
     * File that maintains the requested parse.
     */
    private File file;

    /**
     * Format detected from read.
     */
    private Format format = Format.NONE;
    
    /**
     * @Constructor
     * @param csvFile
     */
    public CsvReader(File file, String exportFormat) {
        this.file = file;
        init(file);
    }

    /**
     * Initialize the read of the csv file. 
     * @param csvFile
     */
    private void init(File csvFile) {

        try {
            Scanner scanner = new Scanner(csvFile);

            if (scanner.hasNextLine()) {
                // Grab the first line from the Scanner to scan the header.
                String line = scanner.nextLine();
                List<String> header = readLine(line);
                try {
                    this.format = detectHeader(header);
                } catch (Exception e) {
                    System.out.println("CsvReader::detectHeader, ERROR - " + e);
                    format = Format.NONE;
                }

                switch (format) {
                    case FT3D: {
                        System.out.println("CsvReader::init, INFO - Format Detected, FT3D");
                        // Hand off the Scanner to the hydrate method.
                        try {
                            List<FT3DRow> ft3dRows = hydrateFT3DdRows(scanner);
                            System.out.println("CsvReader::init, INFO - CSV rows were parsed, and 0 errors were found.");

                            FT3D ft3d = new FT3D(ft3dRows);
                            ft3d.printRowColumnValues();
                            this.csvObject = ft3d;
                        } catch (Exception e) {
                            System.out.println("CsvReader::hydrateFT3DRow, ERROR - " + e);
                        }
                        
                        break;
                    } case UV5R: {
                        // TODO: Implement.
                        System.out.println("CsvReader::init, INFO - Format Detected, UV5R");
                        break;
                    } case NONE: {
                        // TODO: Implement.
                        System.out.println("CsvReader::init, INFO - Format Detected, NONE");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("CsvReader::init, ERROR - File, " +  this.file.getPath() + ", could not be found.");
        }
    }

    /**
     * Verify that the headers match what is expected from any format.
     * @param csv
     */
    public Format detectHeader(List<String> csv) throws Exception {

        int ft3dHeaderSize = FT3D.Column.HEADER_SIZE;
        int uv5rHeaderSize = UV5R.Column.HEADER_SIZE;

        // In all FT3D headers, there is an empty header value for the INDEX.
        // We should omit this count (-1) since it will not be read in.
        if (csv.size() - 1 == ft3dHeaderSize) {


            final String[] removedValues = {
                /**
                 * For FT3D Headers, the following values are being removed before validation:
                 * 1. The INDEX value is empty.
                 * 2. The last value parsed, in which doesn't represent anything; 
                 * RT Systems software appends an empty value at the end of every row, which is bad.
                 */
                csv.remove(ft3dHeaderSize)
            };

            for (String value: removedValues) {
                if (value != "") {
                    throw new Exception("Header values in a presumed FT3D file were removed incorrectly.");
                }
            }

            int errorIndex = Validator.validateHeader(csv, Format.FT3D);

            if (errorIndex >= 0) {

                throw new Exception("Parsing error in FT3D header: \n" + 
                    "Index: " + errorIndex + "\n" +
                    "Expected Value: " + FT3D.Column.getColumnAtIndex(errorIndex).getLabel() + "\n" +
                    "Found Value: " + csv.get(errorIndex));
            } else {
                return Format.FT3D;
            }
        } else if (csv.size() == uv5rHeaderSize) {
            int errorIndex = Validator.validateHeader(csv, Format.UV5R);

            if (errorIndex >= 0) {
                throw new Exception("Parsing error in UV5R header at index: " + errorIndex);
            } else {
                return Format.UV5R;
            }
        } else {
            System.out.println("There was a problem detecting the header:\n" +
                "Csv Header Length: " + csv.size() + "\n" + 
                "FT3D Header Length: " + ft3dHeaderSize + "\n" + 
                "UV5R Header Length: " + uv5rHeaderSize + "\n");
            return Format.NONE;
        }
    }

    

    /**
     * Reads in a single csv line, and returns a list of Strings representing column values.
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

    private List<FT3DRow> hydrateFT3DdRows(Scanner scanner) throws Exception {
        List<FT3DRow> ft3dRows = new ArrayList<>();

        for (int i = 0; scanner.hasNextLine(); i++) {
            String line = scanner.nextLine();
            
            List<String> row = readLine(line);
            row.remove(48); // Remove random empty value at the end of the list.


            // FT3D Formatting is weird, it populates the entire CSV with all 999 positions, even if they are empty.
            if (!Validator.isEmptyRow(row)) {

                int errorIndex = 0;

                try {
                    errorIndex = Validator.validateRow(row, Format.FT3D);
                } catch (Exception e) {
                    System.out.println(e);
                }
                

                if (errorIndex >= 0) {
                    throw new Exception("Validation error while parsing FT3D CSV: \n" +
                    "Tried to match: " + FT3D.Column.getValidatorAtIndex(errorIndex) + "\n" +
                    "Against: " + row.get(errorIndex) + "\n" +
                    "Row: " + i + "\n" +
                    "Column: " + errorIndex);
                } else {
                    FT3DRow ft3dRow = new FT3DRow(row);
                    ft3dRows.add(ft3dRow);
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
