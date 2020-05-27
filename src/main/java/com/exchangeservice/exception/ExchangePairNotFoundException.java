package com.exchangeservice.exception;

import com.exchangeservice.model.Currency;

public class ExchangePairNotFoundException extends ExchangeServiceException {
    public ExchangePairNotFoundException(Currency first, Currency second) {
        super(String.format("Could not find pair for currencies: %s, %s", first, second));
    }
}
