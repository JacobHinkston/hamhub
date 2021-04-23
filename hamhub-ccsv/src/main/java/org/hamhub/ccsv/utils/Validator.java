package org.hamhub.ccsv.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamhub.api.yeasu.FT3DColumn;
import org.hamhub.api.baofeng.UV5RColumn;
import org.hamhub.api.Format;


public class Validator {

    private final Map<Integer, String> FT3D_VALIDATOR_BY_INDEX = FT3DColumn.getValidatorsByIndex();

    /**
     * @param csv The List<String> That represents the csv header.
     * @return
     */
    public static int validateHeader(List<String> csv, Format format) throws Exception {
        
        List<String> labels;

        switch (format) {
            case FT3D: {
                labels = FT3DColumn.LABELS;
                break;
            } case UV5R: {
                labels = UV5RColumn.LABELS;
                break;
            } default: {
                throw new Exception("Validator::validateHeader, ERROR - provided format " + format + ", is not valid");
            }
        }

        for (int i = 0; i < csv.size(); i++) {
            if (!csv.get(i).equals(labels.get(i))) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Detect if a row is bad. Useful for ending FT3DColumn hydration.
     * Rt Systems appends all empty columns up to 999.
     * @param column
     */
    public static boolean isEmptyRow(List<String> row) {

        // TODO: Find a better way to calculate this.
        
        final int tolerance = 7;
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
}
