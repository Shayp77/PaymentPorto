package com.asin.PayementPorto.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public record Wallet(
        UUID id,
        String userId,
        String accountNumber,
        BigDecimal balance,
        String status,
        Long version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
