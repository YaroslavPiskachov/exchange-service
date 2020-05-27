package com.exchangeservice.dto;

import com.exchangeservice.model.Currency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class Commission {

    @DecimalMin(value = "0.0", message = "Commission can not be less than 0")
    @DecimalMax(value = "100.0", message = "Commission can not be bigger than 100")
    @NotNull(message = "Commission should be provided")
    private BigDecimal commissionPt;

    @NotNull(message = "Currency from is unknown or null")
    private Currency from;

    @NotNull(message = "Currency to is unknown or null")
    private Currency to;
}
