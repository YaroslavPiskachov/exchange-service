package com.exchangeservice.exception;

import com.exchangeservice.model.Currency;

public class PairCommissionIsNullException extends ExchangeServiceException {
    public PairCommissionIsNullException(Currency first, Currency second) {
        super(String.format("Commission for currency pair: %s, %s is not defined", first, second));
    }
}

