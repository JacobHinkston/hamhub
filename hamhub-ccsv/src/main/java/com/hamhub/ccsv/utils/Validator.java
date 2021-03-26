package com.hamhub.ccsv.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hamhub.api.baofeng.UV5R;
import com.hamhub.api.yeasu.FT3D;
import com.hamhub.api.yeasu.FT3DRow;
import com.hamhub.ccsv.common.CsvReader.Format;


public class Validator {

    private final Map<Integer, String> FT3D_VALIDATOR_BY_INDEX = FT3D.Column.getValidatorsByIndex();

    /**
     * @param csv The List<String> That represents the csv header.
     * @return
     */
    public static int validateHeader(List<String> csv, Format format) throws Exception {
        
        List<String> labels;

        switch (format) {
            case FT3D: {
                labels = FT3D.Column.getLabels();
                break;
            } case UV5R: {
                labels = UV5R.Column.getLabels();
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

    }

    /**
     * Detect if a row is bad. Useful for ending FT3DColumn hydration.
     * Rt Systems appends all empty columns up to 999.
     * @param column
     */
    public static boolean isEmptyRow(List<String> row) {

        final int tolerance = 3;
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
