package com.asin.PayementPorto.repository;

import com.asin.PayementPorto.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final JdbcClient jdbcClient;
    public void createTransaction(Transaction transaction){
        String sql= """
                INSERT INTO transactions
                            (id, reference_no, sender_wallet_id, receiver_wallet_id, amount, type, status,created_at)
                            VALUES (?, ?, ?, ?, ?, ?, ?,?)
                """;
        jdbcClient.sql(sql)
                .param(1,transaction.id())
                .param(2,transaction.referenceNo())
                .param(3,transaction.senderWalleId())
                .param(4,transaction.receiverWalletId())
                .param(5,transaction.amount())
                .param(6,transaction.type())
                .param(7,transaction.status())
                .param(8,transaction.createdAt())
                .update();
    }
}
