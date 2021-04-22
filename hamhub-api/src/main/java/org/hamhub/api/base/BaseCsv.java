package org.hamhub.api.base;

import org.hamhub.api.Format;

import java.util.List;

public class BaseCsv {

    private Format format;

    private List<? extends BaseRow> rows;

    public BaseCsv(List<? extends BaseRow> rows) {
        this.rows = rows;
    }
}
