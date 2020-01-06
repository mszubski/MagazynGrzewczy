package com.vistula.magazyn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class KursyWalutSymboleFixerModel {
    private String success;
    @JsonProperty("symbols")
    private JsonNode symbols;

    public KursyWalutSymboleFixerModel() {
    }

    public KursyWalutSymboleFixerModel(String success, JsonNode symbols) {
        this.success = success;
        this.symbols = symbols;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public JsonNode getSymbols() {
        return symbols;
    }

    public void setSymbols(JsonNode symbols) {
        this.symbols = symbols;
    }
}
