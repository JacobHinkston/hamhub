package org.hamhub.api.yeasu;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamhub.api.base.BaseRow;
import org.hamhub.api.Format;
import org.hamhub.api.baofeng.UV5R;
import org.hamhub.common.frequency.Frequency;

public class FT3DRow extends BaseRow {

   public FT3DRow(List<String> row) {
      super(row);
      setFormat(Format.FT3D);
   }

   @Override
   public int init() {
      
      // Check to make sure the row size is correct.
      if (row.size() > FT3DColumn.HEADER_SIZE) {
         System.err.println("FT3DRow::init, ERROR - FT3D Row size was incorrect.");
         return 0;
      }


      for (int i = 0; i < row.size(); i++) {

         // Capture the Value and the column-type.
         String val = row.get(i);
         FT3DColumn column = FT3DColumn.getColumnAtIndex(i);

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
               Frequency rxFrequency = new Frequency(val);
               setRxFrequency(rxFrequency);
               break;
            case 2:
               Frequency txFrequency = new Frequency(val);
               setTxFrequency(txFrequency);
               break;
            case 3:
               Frequency offsetFrequency = new Frequency(val);
               setOffsetFrequency(offsetFrequency);
               break;
            case 4:
               setOffsetDirection(val);
               break;
            case 5:
               setMode(val);
               break;
            case 7:
               setChannelName(val);
               break;
            case 8:
               setToneMode(val);
               break;
            case 9:
               Frequency txCtcss = new Frequency(val);
               setTxCtcss(txCtcss);
               break;
            case 14: 
               Frequency rxCtcss = new Frequency(val);
               setRxCtcss(rxCtcss);
               break;
            case 47:
               setComment(val);
               break;
            default:
               addMetaData(column.getLabel(), val);
         }
      }

      return -1;
   }
}
