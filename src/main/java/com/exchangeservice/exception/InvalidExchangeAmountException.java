package com.exchangeservice.exception;

import java.math.BigDecimal;

public class InvalidExchangeAmountException extends ExchangeServiceException {
    public InvalidExchangeAmountException(BigDecimal amount) {
        super(String.format("Given amount: %s could not be exchanged", amount));
    }
}
