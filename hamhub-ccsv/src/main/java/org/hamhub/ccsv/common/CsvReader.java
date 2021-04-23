package org.hamhub.ccsv.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hamhub.api.baofeng.UV5RColumn;
import org.hamhub.api.yeasu.FT3DColumn;
import org.hamhub.api.Format;
import org.hamhub.ccsv.utils.Validator;

import java.io.File;
import java.io.FileNotFoundException;

public class CsvReader {

    /**
     * Format detected from read.
     */
    private Format format = Format.NONE;

    /**
     * Holds the CSV
     */
    private List<List<String>> csv;
    
    /**
     * @Constructor
     * @param csvFile
     */
    public CsvReader(File file) {
        init(file);
    }

    /**
     * Returns the csv.
     * @return
     */
    public List<List<String>> getCsv() {
        return this.csv;
    }

    /**
     * Returns the Format of the read csv.
     * @return
     */
    public Format getFormat() {
        return this.format;
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
                    this.format = detectFormat(header);
                    if (format != Format.NONE) {
                        this.csv = readLines(scanner);
                    } else {
                        System.err.println("CcsvReader::init, ERROR - The CSV read had no format.");
                    }
                    
                } catch (Exception e) {
                    System.err.println("CsvReader::detectHeader, ERROR - " + e);
                    format = Format.NONE;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("CsvReader::init, ERROR - File, " +  csvFile.getPath() + ", could not be found.");
        }
    }

    /**
     * Verify that the headers match what is expected from any format.
     * @param csv
     */
    public Format detectFormat(List<String> header) throws Exception {

        int ft3dHeaderSize = FT3DColumn.HEADER_SIZE;
        int uv5rHeaderSize = UV5RColumn.HEADER_SIZE;

        // In all FT3D headers, there is an empty header value for the INDEX.
        // We should omit this count (-1) since it will not be read in.
        if (header.size() - 1 == ft3dHeaderSize) {

            final String[] removedValues = {
                /**
                 * For FT3D Headers, the following values are being removed before validation:
                 * 1. The INDEX value is empty.
                 * 2. The last value parsed, in which doesn't represent anything; 
                 * RT Systems software appends an empty value at the end of every row, which is bad.
                 */
                header.remove(ft3dHeaderSize)
            };

            for (String value: removedValues) {
                if (value != "") {
                    throw new Exception("Header values in a presumed FT3D file were removed incorrectly.");
                }
            }

            int errorIndex = Validator.validateHeader(header, Format.FT3D);

            if (errorIndex >= 0) {
                throw new Exception("Parsing error in FT3D header: \n" + 
                    "Index: " + errorIndex + "\n" +
                    "Expected Value: " + FT3DColumn.getColumnAtIndex(errorIndex).getLabel() + "\n" +
                    "Found Value: " + header.get(errorIndex));
            } else {
                return Format.FT3D;
            }
        } else if (header.size() == uv5rHeaderSize) {
            int errorIndex = Validator.validateHeader(header, Format.UV5R);

            if (errorIndex >= 0) {
                throw new Exception("Parsing error in UV5R header at index: " + errorIndex);
            } else {
                return Format.UV5R;
            }
        } else {
            System.err.println("There was a problem detecting the header:\n" +
                "Csv Header Length: " + header.size() + "\n" + 
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
    private List<String> readLine(String line) {
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

    /**
     * Returns a parsed list of lists containing strings. A 2D Array representing the entire CSV.
     * @param scanner
     * @return
     */
    private List<List<String>> readLines(Scanner scanner) {
        List<List<String>> csv = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<String> row = readLine(line);

            // We do want to catch if the row is empty.
            // In the slight chance there are values at the end of the CSV,
            // We don't just want to skip them, add them.
            if (!Validator.isEmptyRow(row)) {
                csv.add(row);
            }  
        }
        return csv;
    }
}
