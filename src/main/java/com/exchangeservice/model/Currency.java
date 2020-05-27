package com.exchangeservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {

    EUR("EUR"),

    USD("USD"),

    UAH("UAH"),

    RUB("RUB");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static Currency fromValue(String text) {
        for (Currency b : Currency.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
