package com.asin.PayementPorto.service;

import com.asin.PayementPorto.dto.TopUpRequest;
import com.asin.PayementPorto.dto.TransferRequest;
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
    @Transactional
    public UUID transfer(TransferRequest request){
        if (request.amount().compareTo(BigDecimal.ZERO)<=0){
            throw  new RuntimeException("Nominal transfer harus lebih besar dari 0");
        }
        if (request.fromAccountNumber().equals(request.toAccountNumber())){
            throw new RuntimeException("TIdak bisa transfer ke rekening sendiri");
        }
        WalletDataResponse sender=walletService.getWallet(request.fromAccountNumber());
        WalletDataResponse receiver=walletService.getWallet(request.toAccountNumber());
        if (sender.balance().compareTo(request.amount())<=0){
            throw new RuntimeException("Saldo tidak mencukupi untuk melakukan transfer");
        }
        BigDecimal newSender=sender.balance().subtract(request.amount());
        BigDecimal newReceiver=receiver.balance().add(request.amount());
        walletService.updateBalance(sender.id(),newSender);
        walletService.updateBalance(receiver.id(),newReceiver);
        String referenceNo="TRF-"+System.currentTimeMillis();
        Transaction tf=new Transaction(
                UUID.randomUUID(),
                referenceNo,
                sender.id(),
                receiver.id(),
                request.amount(),
                "TRANSFER",
                "SUCCESS",
                LocalDateTime.now()
        );
        transactionRepository.createTransaction(tf);
        return tf.id();
    }
}