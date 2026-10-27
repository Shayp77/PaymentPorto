package com.asin.PayementPorto.service;

import com.asin.PayementPorto.dto.TopUpRequest;
import com.asin.PayementPorto.dto.WalletDataResponse;
import com.asin.PayementPorto.model.Transaction;
import com.asin.PayementPorto.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    @Transactional
    public UUID topUpTransaction(TopUpRequest request){

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Nominal Top Up harus lebih besar dari 0");
        }

        WalletDataResponse wallet = walletService.getWallet(request.accountNumber());

        BigDecimal newBalance = wallet.balance().add(request.amount());

        walletService.updateBalance(wallet.id(), newBalance);

        String referenceNo = "TOPUP-" + System.currentTimeMillis();
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                referenceNo,
                null,
                wallet.id(),
                request.amount(),
                "TOP_UP",
                "SUCCESS",
                LocalDateTime.now()
        );
        transactionRepository.createTransaction(transaction);

        return transaction.id();
    }
}