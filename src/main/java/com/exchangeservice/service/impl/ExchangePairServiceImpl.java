package com.exchangeservice.service.impl;

import com.exchangeservice.exception.*;
import com.exchangeservice.dto.Commission;
import com.exchangeservice.dto.Exchange;
import com.exchangeservice.dto.ExchangeRate;
import com.exchangeservice.dto.OperationType;
import com.exchangeservice.model.Currency;
import com.exchangeservice.model.ExchangePair;
import com.exchangeservice.repository.ExchangePairRepository;
import com.exchangeservice.service.ExchangePairService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangePairServiceImpl implements ExchangePairService {

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final Integer SCALE = 2;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final ExchangePairRepository exchangePairRepository;

    public ExchangePairServiceImpl(ExchangePairRepository exchangePairRepository) {
        this.exchangePairRepository = exchangePairRepository;
    }

    @Override
    @Transactional
    public Commission saveCommission(Commission commission) {
        BigDecimal commissionPt = commission.getCommissionPt();
        Currency from = commission.getFrom();
        Currency to = commission.getTo();

        saveCommission(commissionPt, from, to);
        saveCommission(commissionPt, to, from);

        return commission;
    }

    @Override
    public List<Commission> getCommissions() {
        return exchangePairRepository.findAll().stream()
                .map(this::exchangePairToCommission)
                .collect(Collectors.toList());
    }

    @Override
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws ExchangePairNotFoundException {
        BigDecimal rate = exchangeRate.getRate();
        Currency from = exchangeRate.getFrom();
        Currency to = exchangeRate.getTo();

        saveExchangeRate(from, to, rate);
        saveExchangeRate(to, from, BigDecimal.ONE.divide(rate, MathContext.DECIMAL32));

        return exchangeRate;
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangePairRepository.findAll().stream()
                .map(this::exchangePairToExchangeRate)
                .collect(Collectors.toList());
    }

    @Override
    public Exchange exchange(Exchange exchange) throws ExchangeServiceException {

        if (exchange.getOperationType().equals(OperationType.GET) && validateAmount(exchange.getAmountTo())) {
            BigDecimal amountTo = exchange.getAmountTo();
            exchange.setAmountTo(round(amountTo));
            exchange.setAmountFrom(round(exchange(exchange.getCurrencyTo(), exchange.getCurrencyFrom(), amountTo)));
        } else if (exchange.getOperationType().equals(OperationType.GIVE) && validateAmount(exchange.getAmountFrom())) {
            BigDecimal amountFrom = exchange.getAmountFrom();
            exchange.setAmountFrom(round(amountFrom));
            exchange.setAmountTo(round(exchange(exchange.getCurrencyFrom(), exchange.getCurrencyTo(), amountFrom)));
        }

        return exchange;
    }

    private void validateExchangePair(ExchangePair exchangePair) throws PairRateIsNullException, PairCommissionIsNullException {
        Currency currencyFrom = exchangePair.getCurrencyFrom();
        Currency currencyTo = exchangePair.getCurrencyTo();
        if (exchangePair.getRate() == null) {
            throw new PairRateIsNullException(currencyFrom, currencyTo);
        }

        if (exchangePair.getCommissionPt() == null) {
            throw new PairCommissionIsNullException(currencyFrom, currencyTo);
        }
    }

    private Commission exchangePairToCommission(ExchangePair exchangePair) {
        return Commission.builder()
                .from(exchangePair.getCurrencyFrom())
                .to(exchangePair.getCurrencyTo())
                .commissionPt(exchangePair.getCommissionPt())
                .build();
    }

    private ExchangeRate exchangePairToExchangeRate(ExchangePair exchangePair) {
        return ExchangeRate.builder()
                .from(exchangePair.getCurrencyFrom())
                .to(exchangePair.getCurrencyTo())
                .rate(exchangePair.getRate())
                .build();
    }

    private boolean validateAmount(BigDecimal amount) throws InvalidExchangeAmountException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidExchangeAmountException(amount);
        }

        return true;
    }

    private void saveExchangeRate(Currency from, Currency to, BigDecimal rate) throws ExchangePairNotFoundException {
        Optional<ExchangePair> exchangePairOptional = exchangePairRepository.getByCurrencyFromAndCurrencyTo(from, to);
        ExchangePair exchangePair;

        if (exchangePairOptional.isPresent()) {
            exchangePair = exchangePairOptional.get();
            exchangePair.setRate(rate);
        } else {
            exchangePair = new ExchangePair();
            exchangePair.setRate(rate);
            exchangePair.setCurrencyFrom(from);
            exchangePair.setCurrencyTo(to);
        }

        exchangePairRepository.save(exchangePair);
    }

    private BigDecimal round(BigDecimal value) {
        return value.setScale(SCALE, ROUNDING_MODE);
    }

    private void saveCommission(BigDecimal commissionPt, Currency from, Currency to) {
        Optional<ExchangePair> exchangePairOptional = exchangePairRepository.getByCurrencyFromAndCurrencyTo(from, to);
        if (exchangePairOptional.isPresent()) {
            ExchangePair exchangePair = exchangePairOptional.get();
            exchangePair.setCommissionPt(commissionPt);
            exchangePairRepository.save(exchangePair);
        } else {
            ExchangePair exchangePair = new ExchangePair();
            exchangePair.setCommissionPt(commissionPt);
            exchangePair.setCurrencyFrom(from);
            exchangePair.setCurrencyTo(to);
        }
    }

    private BigDecimal exchange(Currency from, Currency to, BigDecimal amount) throws ExchangePairNotFoundException, PairRateIsNullException, PairCommissionIsNullException {
        ExchangePair exchangePair = exchangePairRepository.getByCurrencyFromAndCurrencyTo(from, to)
                .orElseThrow(() -> new ExchangePairNotFoundException(from, to));

        validateExchangePair(exchangePair);

        BigDecimal commissionedAmount = amount.subtract(percentage(amount, exchangePair.getCommissionPt()));
        return commissionedAmount.multiply(exchangePair.getRate());
    }

    private BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED, ROUNDING_MODE);
    }
}
