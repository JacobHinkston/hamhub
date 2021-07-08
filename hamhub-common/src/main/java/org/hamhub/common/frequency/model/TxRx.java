package org.hamhub.common.frequency.model;

import org.hamhub.common.frequency.Frequency;
import org.hamhub.common.frequency.FrequencyType;

public class TxRx extends Frequency {

    public TxRx(String frequency) {
        super(frequency);

        FrequencyType[] frequencyTypes = {
            FrequencyType.TX_RX
        };

        boolean valid = init(frequencyTypes);

        if (!valid) {
            System.err.println("Step Frequency: " + frequency + " is not valid.");
        }
    }
}
