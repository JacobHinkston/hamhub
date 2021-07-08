package org.hamhub.common.frequency;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frequency {

    protected String frequency;

    protected String units;

    protected FrequencyType frequencyType;

    public Frequency(String frequency) {
        this.frequency = frequency;
    }

    protected boolean init(FrequencyType[] frequencyTypes) {
        boolean match = false;
        Pattern pattern = null;
        Matcher matcher = null;

        for (FrequencyType type: frequencyTypes) {
            pattern = Pattern.compile(type.getValidator());
            matcher = pattern.matcher(frequency);

            if (matcher.find()) {
                frequencyType = type;
                match = true;
            }
        }

        return match;
    }
}
