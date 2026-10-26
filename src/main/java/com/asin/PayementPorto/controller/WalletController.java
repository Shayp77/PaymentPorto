package com.asin.PayementPorto.controller;

import com.asin.PayementPorto.dto.CreateWalletRequest;
import com.asin.PayementPorto.dto.WalletDataResponse;
import com.asin.PayementPorto.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    @GetMapping("/{accountNumber}")
    public ResponseEntity<WalletDataResponse> getWallet(@PathVariable String accountNumber){
        WalletDataResponse walletDataResponse=walletService.getWallet(accountNumber);
        return ResponseEntity.ok(walletDataResponse);
    }
    @PostMapping
    public ResponseEntity<UUID> createWallet(@Valid @RequestBody CreateWalletRequest request){
        UUID newWalletId= walletService.createWallet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWalletId);
    }
}
