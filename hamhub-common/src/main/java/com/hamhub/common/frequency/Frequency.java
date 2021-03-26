package com.hamhub.common.frequency;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frequency {

    private String frequency;

    private FrequencyType type;

    public String units;

    public Frequency(String frequency) {
        this.frequency = frequency;
    }

    public void init(String frequency) {
        boolean match = false;
        String regex = null;
        Pattern pattern = null;

        for (FrequencyType type: FrequencyType.values()) {
            regex = type.getValidator();
            pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(frequency);

            if (matcher.find()) {
                match = true;
                this.type = type;
            }
        }
    }

    public static Frequency of(Frequency frequency) {
        return new Frequency("");
    }


    public String to(FrequencyType from, FrequencyType to) {
        return "";
    }
}
