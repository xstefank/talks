package org.assemble.model;

import org.assemble.Avenger;

import java.util.List;

public class DataTable {

    private long draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<Avenger> data;
    private String error;

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Avenger> getData() {
        return data;
    }

    public void setData(List<Avenger> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

