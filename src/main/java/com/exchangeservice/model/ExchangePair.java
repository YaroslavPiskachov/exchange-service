package com.exchangeservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class ExchangePair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Currency currencyFrom;

    @Column(nullable = false)
    private Currency currencyTo;

    @Column
    private BigDecimal commissionPt;

    @Column
    private BigDecimal rate;
}
