package com.asin.PayementPorto.repository;

import com.asin.PayementPorto.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WalletRepository {
    private final JdbcClient jdbcClient;
    public UUID createWallet(String accountNumber,String userId){
        String sql= """
                Insert into Wallets(id,user_id,account_number,balance,status,version,created_at,updated_at)
                Values (:id,:userId, :accountNumber, :balance,:status,:version,:createdAt,:updatedAt)
                """;
        UUID walletId=UUID.randomUUID();
        jdbcClient.sql(sql)
                .param("id", walletId)
                .param("userId",userId)
                .param("accountNumber",accountNumber)
                .param("balance", BigDecimal.ZERO)
                .param("status","ACTIVE")
                .param("version",1L)
                .param("createdAt", LocalDateTime.now())
                .param("updatedAt", LocalDateTime.now())
                .update();
        return walletId;
    }
    public Optional<Wallet> getWallet(String accountNumber){
        String sql= """
                Select * From Wallets where 
                account_number= :accountNumber
                """;
        return jdbcClient.sql(sql)
                .param("accountNumber",accountNumber)
                .query(Wallet.class)
                .optional();
    }
    public Optional<Wallet> getWalletForUpdate(String accountNumber){
        String sql="Select * From wallets where account_number=? FOR UPDATE";
        return jdbcClient.sql(sql)
                .param(accountNumber)
                .query(Wallet.class)
                .optional();
    }
    public void updateBalance(UUID walletid, BigDecimal newBalance){
        String sql="Update wallets Set balance=?, updated_at=CURRENT_TIMESTAMP WHERE id=?";
        jdbcClient.sql(sql)
                .param(1,newBalance)
                .param(2,walletid)
                .update();
    }
}
