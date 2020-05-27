package com.exchangeservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperationType {
    GET, GIVE;

    @JsonCreator
    public static OperationType fromValue(String text) {
        for (OperationType b : OperationType.values()) {
            if (String.valueOf(b.toString()).equals(text)) {
                return b;
            }
        }

        return null;
    }
}
