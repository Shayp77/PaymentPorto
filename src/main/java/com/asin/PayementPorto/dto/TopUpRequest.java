package com.asin.PayementPorto.dto;

import java.math.BigDecimal;

public record TopUpRequest(
        String accountNumber,
        BigDecimal amount
) {
}
