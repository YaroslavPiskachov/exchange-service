package com.exchangeservice.dto;

import com.exchangeservice.model.Currency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRate {

    @NotNull(message = "Rate is null")
    @DecimalMin(value = "0.0", message = "Rate can not be less than 0")
    private BigDecimal rate;

    @NotNull(message = "Currency from is unknown or null")
    private Currency from;

    @NotNull(message = "Currency to is unknown or null")
    private Currency to;
}
