package org.hamhub.common.frequency.model;

import org.hamhub.common.frequency.Frequency;
import org.hamhub.common.frequency.FrequencyType;

public class Offset extends Frequency {

    public Offset(String frequency) {
        super(frequency);

        FrequencyType[] frequencyTypes = {
            FrequencyType.OFFSET,
            FrequencyType.OFFSET_UNIT
        };

        boolean valid = validate(frequencyTypes);

        if (!valid) {
            System.err.println("Offset Frequency: " + frequency + " is not valid.");
        } else {
            init();
        }
    }

    private void init() {
        
    }
}
