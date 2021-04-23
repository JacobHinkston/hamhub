package org.hamhub.common.frequency;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.GroupLayout.Group;

public class TxRx implements Frequency {

    public static enum Type {

        /**
         * Only acceptable TX_RX frequency
         * 146.64000
         * 50.00000
         */
        TX_RX("([0-9]{1,3})\\.([0-9]{2}(5|0)[0]{2})");

        private String validator;
        private int[] figures;
        private Type(String validator, int[] figures) { 
            this.validator = validator; 
            this.figures = figures;
        }
        public String getValidator() { return validator; }
        public int[] getFigures() { return this.figures; }
    }

    public TxRx(String frequency) {
        this.frequency = frequency;
        init();
    }

    private void init() {

        String regex = null;
        Pattern pattern = null;

        for (Type type: Type.values()) {
            pattern = Pattern.compile(type.getValidator());
            Matcher matcher = pattern.matcher(frequency);

            if (matcher.find()) {
                
            }
        }
        
    }
}
