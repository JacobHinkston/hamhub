package org.hamhub.common.frequency.model;

import java.util.List;

import org.hamhub.common.frequency.Frequency;
import org.hamhub.common.frequency.FrequencyType;

public class Ctcss extends Frequency {

    public Ctcss(String frequency) {
        super(frequency);

        FrequencyType[] frequencyTypes = {
            FrequencyType.CTCSS
        };

        boolean valid = init(frequencyTypes);

        if (!valid) {
            System.err.println("CTCSS Frequency: " + frequency + " is not valid.");
        }
    }

}
