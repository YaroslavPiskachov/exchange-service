package com.exchangeservice.repository;

import com.exchangeservice.model.Currency;
import com.exchangeservice.model.ExchangePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangePairRepository extends JpaRepository<ExchangePair, Long> {
    Optional<ExchangePair> getByCurrencyFromAndCurrencyTo(Currency from, Currency to);
}
