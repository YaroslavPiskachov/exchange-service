package com.exchangeservice.controller;

import com.exchangeservice.dto.Commission;
import com.exchangeservice.service.ExchangePairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommissionController {

    private ExchangePairService exchangePairService;

    @Autowired
    public CommissionController(ExchangePairService exchangePairService) {
        this.exchangePairService = exchangePairService;
    }

    @GetMapping("/commissions")
    public List<Commission> getCommissionsList() {
        return exchangePairService.getCommissions();
    }

    @PostMapping("/commissions")
    public Commission postCommission(@RequestBody @Valid Commission commission) {
        return exchangePairService.saveCommission(commission);
    }
}
