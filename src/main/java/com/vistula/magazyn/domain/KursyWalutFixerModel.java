package com.vistula.magazyn.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class KursyWalutFixerModel {
    private String success;
    private Timestamp timestamp;
    private String base;
    private Date date;
    private Rates rates;

    public KursyWalutFixerModel() {
    }

    public KursyWalutFixerModel(String success, Timestamp timestamp, String base, Date date, Rates rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "KursyWalutFixerModel{" +
            "success='" + success + '\'' +
            ", timestamp=" + timestamp +
            ", base='" + base + '\'' +
            ", date=" + date +
            ", rates='" + rates + '\'' +
            '}';
    }
}
