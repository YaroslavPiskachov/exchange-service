package com.exchangeservice.controller;

import com.exchangeservice.dto.Exchange;
import com.exchangeservice.exception.ExchangeServiceException;
import com.exchangeservice.service.ExchangePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExchangeController {

    private ExchangePairService exchangePairService;

    @Autowired
    public ExchangeController(ExchangePairService exchangePairService) {
        this.exchangePairService = exchangePairService;
    }

    @PostMapping("/exchange")
    public Exchange exchange(@Valid @RequestBody Exchange exchange) throws ExchangeServiceException {
        return exchangePairService.exchange(exchange);
    }


}
