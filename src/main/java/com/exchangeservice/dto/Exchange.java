package com.exchangeservice.dto;

import com.exchangeservice.model.Currency;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Exchange {

    @DecimalMin(value = "0.0", message = "Amount from can not be less than 0")
    private BigDecimal amountFrom;

    @DecimalMin(value = "0.0", message = "Amount to can not be less than 0")
    private BigDecimal amountTo;

    @NotNull(message = "Currency from is unknown or null")
    private Currency currencyFrom;

    @NotNull(message = "Currency to is unknown or null")
    private Currency currencyTo;

    @NotNull(message = "Operation type is unknown or null")
    private OperationType operationType;

}
