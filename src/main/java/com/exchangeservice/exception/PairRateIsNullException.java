package com.exchangeservice.exception;

import com.exchangeservice.model.Currency;

public class PairRateIsNullException extends ExchangeServiceException {
    public PairRateIsNullException(Currency first, Currency second) {
        super(String.format("Rate for currency pair: %s, %s is not defined", first, second));
    }
}
