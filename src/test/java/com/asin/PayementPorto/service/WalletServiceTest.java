package com.asin.PayementPorto.service;

import com.asin.PayementPorto.dto.CreateWalletRequest;
import com.asin.PayementPorto.dto.WalletDataResponse;
import com.asin.PayementPorto.model.Wallet;
import com.asin.PayementPorto.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;

    @Test
    void createWallet_ShouldReturnUuid_WhenAccountNumberIsUnique(){
        CreateWalletRequest request= new CreateWalletRequest("UUID","Test123");
        UUID expected= UUID.randomUUID();
        when(walletRepository.getWallet("UUID")).thenReturn(Optional.empty());
        when(walletRepository.createWallet("UUID","Test123")).thenReturn(expected);
        UUID actualId= walletService.createWallet(request);
        assertNotNull(actualId);
        assertEquals(expected,actualId);
        verify(walletRepository,times(1)).createWallet("UUID","Test123");

    }
    @Test
    void createWallet_ShouldThrowException_WhenAccountNumberAlreadyExist(){
        CreateWalletRequest request = new CreateWalletRequest("UUID","Test123");
        Wallet existingWallet = new Wallet(
                UUID.randomUUID(), "orang-lain", "UUID",
                BigDecimal.ZERO, "ACTIVE", 1L, LocalDateTime.now(), LocalDateTime.now()
        );
        when(walletRepository.getWallet("UUID")).thenReturn(Optional.of(existingWallet));
        RuntimeException exception=assertThrows(RuntimeException.class,()->{
            walletService.createWallet(request);
        });
        assertEquals("Account Number sudah digunakan",exception.getMessage());
        verify(walletRepository,never()).createWallet(anyString(),anyString());
    }
    @Test
    void getWallet_ShouldReturnWallet_WhenAccountNumberExisted(){
        String accountNumber = "UUID";
        Wallet existingWallet = new Wallet(
                UUID.randomUUID(), "orang-lain", "UUID",
                BigDecimal.ZERO, "ACTIVE", 1L, LocalDateTime.now(), LocalDateTime.now()
        );
        when(walletRepository.getWallet(accountNumber)).thenReturn(Optional.of(existingWallet));
        WalletDataResponse response = walletService.getWallet(accountNumber);
        assertEquals(existingWallet.accountNumber(), response.accountNumber());
        assertEquals(existingWallet.userId(), response.userId());
        assertEquals(existingWallet.id(), response.id());
        assertEquals(existingWallet.balance(), response.balance());
        verify(walletRepository, times(1)).getWallet(accountNumber);
    }
}
