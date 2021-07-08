package org.hamhub.common.frequency.model;

import java.util.List;

import org.hamhub.common.frequency.Frequency;
import org.hamhub.common.frequency.FrequencyType;

public class Step extends Frequency {

    public Step(String frequency) {
        super(frequency);

        FrequencyType[] frequencyTypes = {
            FrequencyType.STEP,
            FrequencyType.STEP_UNIT
        };

        boolean valid = init(frequencyTypes);

        if (!valid) {
            System.err.println("Step Frequency: " + frequency + " is not valid.");
        }
    }
}
