package org.hamhub.api.baofeng;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamhub.api.base.BaseRow;
import org.hamhub.api.Format;
import org.hamhub.common.frequency.model.Ctcss;
import org.hamhub.common.frequency.model.Offset;
import org.hamhub.common.frequency.model.TxRx;

public class UV5RRow extends BaseRow {

   public UV5RRow(List<String> row) {
      super(row);
      this.setFormat(Format.UV5R);
   }

   @Override
   public int init() {
      
      // Check to make sure the row size is correct.
      if (row.size() > UV5RColumn.HEADER_SIZE) {
        System.err.println("UV5RRow::init, ERROR - UV5R Row size was incorrect.");
        return 0;
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
                return i;
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
                TxRx rx_txFrequency = new TxRx(val);
                setRxFrequency(rx_txFrequency);
                setTxFrequency(rx_txFrequency);
            case 3:
                setOffsetDirection(val);
                break;
            case 4:
                Offset offsetFrequency = new Offset(val);
                setOffsetFrequency(offsetFrequency);
                break;
            case 5: 
                setToneMode(val);
                break;
            case 6:
                Ctcss txCtcssFrequency = new Ctcss(val);
                setTxCtcss(txCtcssFrequency);
            case 7:
                Ctcss rxCtcssFrequency = new Ctcss(val);
                setRxCtcss(rxCtcssFrequency);
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
      
      return -1;
   }
}
