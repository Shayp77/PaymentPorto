package com.asin.PayementPorto.controller;

import com.asin.PayementPorto.dto.TopUpRequest;
import com.asin.PayementPorto.dto.TransferRequest;
import com.asin.PayementPorto.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;
    @PostMapping("/topup")
    public ResponseEntity<UUID> topUpTransaction(@Valid @RequestBody TopUpRequest request){
        UUID newTransactionId=service.topUpTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransactionId);
    }
    @PostMapping("/transfer")
    public ResponseEntity<UUID> transferTransacion(@Valid @RequestBody TransferRequest request){
        UUID newId=service.transfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newId);
    }
}
