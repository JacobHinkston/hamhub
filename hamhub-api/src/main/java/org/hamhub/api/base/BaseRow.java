package org.hamhub.api.base;

import org.hamhub.api.Format;
import org.hamhub.common.frequency.Frequency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseRow {
    
    private Format format;
    private List<String> row;

    private int index;
    private String channelName;
    private String mode;
    private Frequency rxFrequency;
    private Frequency txFrequency;
    private Frequency offsetFrequency;
    private String offsetDirection;
    private String toneMode;
    private Frequency txCtcss;
    private Frequency rxCtcss;
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

    private void init(List<String> row) {
        // Not Implemented.
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

    public Frequency getRxFrequency() { return rxFrequency; }

    public void setRxFrequency(Frequency rxFrequency) { this.rxFrequency = rxFrequency; }

    public Frequency getTxFrequency() { return txFrequency; }

    public void setTxFrequency(Frequency txFrequency) { this.txFrequency = txFrequency; }

    public Frequency getOffsetFrequency() { return offsetFrequency; }

    public void setOffsetFrequency(Frequency offsetFrequency) { this.offsetFrequency = offsetFrequency; }

    public String getOffsetDirection() { return offsetDirection; }

    public void setOffsetDirection(String offsetDirection) { this.offsetDirection = offsetDirection; }

    public String getToneMode() { return toneMode; }

    public void setToneMode(String toneMode) { this.toneMode = toneMode; }

    public Frequency getTxCtcss() { return txCtcss; }

    public void setTxCtcss(Frequency txCtcss) { this.txCtcss = txCtcss; }

    public Frequency getRxCtcss() { return rxCtcss; }

    public void setRxCtcss(Frequency rxCtcss) { this.rxCtcss = rxCtcss; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Map<String, String> getMetaData() { return metaData; }

    public void setMetaData(Map<String, String> metaData) { this.metaData = metaData; }

    public void addMetaData(String key, String value) { this.metaData.put(key, value); } 
}
