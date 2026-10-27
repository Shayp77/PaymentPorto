package com.asin.PayementPorto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        String referenceNo,
        UUID senderWalleId,
        UUID receiverWalletId,
        BigDecimal amount,
        String type,
        String status,
        LocalDateTime createdAt
) {
}
