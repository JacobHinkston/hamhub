package com.hamhub.api.baofeng;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hamhub.api.base.BaseRow;
import com.hamhub.api.Format;
import com.hamhub.common.frequency.Frequency;

public class UV5RRow extends BaseRow {

   public UV5RRow(List<String> row) {
      super(row);
      this.setFormat(Format.UV5R);
   }

   private void init(List<String> row) {
      
      // Check to make sure the row size is correct.
      if (row.size() < UV5RColumn.HEADER_SIZE) {
        throw new IllegalStateException("UV5RRow::init, ERROR - UV5R Row size was incorrect.");
      }

      for (int i = 0; i < row.size(); i++) {

        // Capture the Value and the column-type.
        String val = row.get(i);
        UV5RColumn column = UV5RColumn.getColumnAtIndex(i);

        String regex = column.getValidator();
        Pattern pattern;

        if (regex != null) {
            pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(val);

            if (!matcher.find()) {
                // TODO: Implement
            }
        }

         switch(i) {
            case 0:
               setIndex(Integer.parseInt(val));
               break;
            case 1: 
                setChannelName(val);
                break;
            case 2:
                Frequency rx_txFrequency = new Frequency(val);
                setRxFrequency(rx_txFrequency);
                setTxFrequency(rx_txFrequency);
            case 3:
                setOffsetDirection(val);
                break;
            case 4:
                Frequency offsetFrequency = new Frequency(val);
                setOffsetFrequency(offsetFrequency);
                break;
            case 5: 
                setToneMode(val);
                break;
            case 6:
                Frequency txFrequency = new Frequency(val);
                setTxCtcss(txFrequency);
            case 7:
                Frequency rxFrequency = new Frequency(val);
                setRxCtcss(rxFrequency);
            case 10:
                setMode(val);
                break;
            case 13: 
                setComment(val);
                break;
            default:
               addMetaData(column.getLabel(), val);
         }
      }
   }
}

