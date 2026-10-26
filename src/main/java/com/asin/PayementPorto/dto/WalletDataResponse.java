package com.asin.PayementPorto.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDataResponse(
        UUID id,
        String userId,
        String accountNumber,
        BigDecimal balance,
        String status
) {
}
