package com.asin.PayementPorto.service;

import com.asin.PayementPorto.dto.CreateWalletRequest;
import com.asin.PayementPorto.dto.WalletDataResponse;
import com.asin.PayementPorto.model.Wallet;
import com.asin.PayementPorto.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    @Transactional
    public UUID createWallet(CreateWalletRequest walletRequest){
        if (walletRepository.getWallet(walletRequest.accountNumber()).isPresent()){
            throw new RuntimeException("Account Number sudah digunakan");
        }
        UUID newWallet= walletRepository.createWallet(walletRequest.accountNumber(),walletRequest.userId());
        return newWallet;
    }
    public WalletDataResponse getWallet(String accountNumber){
        Wallet wallet=walletRepository.getWallet(accountNumber)
                        .orElseThrow(()->new RuntimeException("Account Number Tidak Mempunyai Wallet"));

        return new WalletDataResponse(
                wallet.id(),
                wallet.userId(),
                wallet.accountNumber(),
                wallet.balance(),
                wallet.status()
        );
    }
    public void updateBalance(UUID id, BigDecimal amount){
        walletRepository.updateBalance(id, amount);
    }
}
