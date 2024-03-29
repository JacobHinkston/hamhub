package org.hamhub.api.base;

import org.hamhub.api.Format;
import org.hamhub.common.frequency.Frequency;
import org.hamhub.common.frequency.model.Ctcss;
import org.hamhub.common.frequency.model.Offset;
import org.hamhub.common.frequency.model.TxRx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseRow {
    
    private Format format;
    protected List<String> row;

    private int index;
    private String channelName;
    private String mode;
    private TxRx rxFrequency;
    private TxRx txFrequency;
    private Offset offsetFrequency;
    private String offsetDirection;
    private String toneMode;
    private Ctcss txCtcss;
    private Ctcss rxCtcss;
    private String comment;

    public Map<String, String> metaData = new HashMap<>();

    /**
     * Constructor for a Base Row.
     * Takes a list of columns that represent a row in a CSV.
     * @param columns
     */
    public BaseRow(List<String> row) {
        this.row = row;
    }

    public int init() {
        return -1;
    }

    public void printRow() {

        for (String column: row) {
            System.out.print(column + ", ");
        }

        System.out.println();
    }

    public Format getFormat() { return format; }

    public void setFormat(Format format) { this.format = format; }

    public List<String> getRow() { return row; }

    public void setRow(List<String> row) { this.row = row; }

    public int getIndex() { return index;  }

    public void setIndex(int index) { this.index = index; }

    public String getChannelName() { return channelName; }

    public void setChannelName(String channelName) { this.channelName = channelName; }

    public String getMode() { return mode; }

    public void setMode(String mode) { this.mode = mode; }

    public TxRx getRxFrequency() { return rxFrequency; }

    public void setRxFrequency(TxRx rxFrequency) { this.rxFrequency = rxFrequency; }

    public TxRx getTxFrequency() { return txFrequency; }

    public void setTxFrequency(TxRx txFrequency) { this.txFrequency = txFrequency; }

    public Offset getOffsetFrequency() { return offsetFrequency; }

    public void setOffsetFrequency(Offset offsetFrequency) { this.offsetFrequency = offsetFrequency; }

    public String getOffsetDirection() { return offsetDirection; }

    public void setOffsetDirection(String offsetDirection) { this.offsetDirection = offsetDirection; }

    public String getToneMode() { return toneMode; }

    public void setToneMode(String toneMode) { this.toneMode = toneMode; }

    public Ctcss getTxCtcss() { return txCtcss; }

    public void setTxCtcss(Ctcss txCtcss) { this.txCtcss = txCtcss; }

    public Frequency getRxCtcss() { return rxCtcss; }

    public void setRxCtcss(Ctcss rxCtcss) { this.rxCtcss = rxCtcss; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Map<String, String> getMetaData() { return metaData; }

    public void setMetaData(Map<String, String> metaData) { this.metaData = metaData; }

    public void addMetaData(String key, String value) { this.metaData.put(key, value); } 

}
