package org.hamhub.common.frequency;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frequency {

    protected String frequency;

    protected String units;

    protected boolean init(String frequency, List<FrequencyType> types) {
        boolean match = false;
        String regex = null;
        Pattern pattern = null;

        for (FrequencyType type: types) {
            pattern = Pattern.compile(type.getValidator());
            Matcher matcher = pattern.matcher(frequency);

            if (matcher.find()) {
                match = true;
            }
        }
        return match;
    }

    protected void init(String frequency) {

        String[] validUnits = {
            "kHz",
            "MHz"
        };

        String units = null;
        for (String unit : validUnits) {
            if (frequency.contains(unit)) {
                this.units = units;
            }
        }
    }
}
