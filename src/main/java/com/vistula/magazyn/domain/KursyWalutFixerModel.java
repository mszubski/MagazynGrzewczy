package com.vistula.magazyn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Date;
import java.sql.Timestamp;

public class KursyWalutFixerModel {
    private String success;
    private Timestamp timestamp;
    private String base;
    private Date date;
    @JsonProperty("rates")
    private JsonNode rates;

    public KursyWalutFixerModel() {
    }

    public KursyWalutFixerModel(String success, Timestamp timestamp, String base, Date date, JsonNode rates) {
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

    public JsonNode getRates() {
        return rates;
    }

    public void setRates(JsonNode rates) {
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
