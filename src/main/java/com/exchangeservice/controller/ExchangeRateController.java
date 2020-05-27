package com.exchangeservice.controller;

import com.exchangeservice.dto.ExchangeRate;
import com.exchangeservice.exception.ExchangePairNotFoundException;
import com.exchangeservice.service.ExchangePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExchangeRateController {

    private ExchangePairService exchangePairService;

    @Autowired
    public ExchangeRateController(ExchangePairService exchangePairService) {
        this.exchangePairService = exchangePairService;
    }

    @GetMapping("/exchange-rates")
    public List<ExchangeRate> getExchangeRates() {
        return exchangePairService.getExchangeRates();
    }

    @PostMapping("/exchange-rates")
    public ExchangeRate postExchangeRate(@Valid @RequestBody ExchangeRate exchangeRate) throws ExchangePairNotFoundException {
        return exchangePairService.saveExchangeRate(exchangeRate);
    }
}
