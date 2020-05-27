package com.exchangeservice.service;

import com.exchangeservice.exception.*;
import com.exchangeservice.dto.Commission;
import com.exchangeservice.dto.Exchange;
import com.exchangeservice.dto.ExchangeRate;

import java.util.List;

public interface ExchangePairService {
    Commission saveCommission(Commission commission);
    List<Commission> getCommissions();

    ExchangeRate saveExchangeRate(ExchangeRate rate) throws ExchangePairNotFoundException;
    List<ExchangeRate> getExchangeRates();

    Exchange exchange(Exchange exchange) throws ExchangeServiceException;
}
