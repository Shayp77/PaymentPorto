-- 1. Tabel Wallets (Menyimpan saldo & version)
CREATE TABLE wallets (
                         id UUID PRIMARY KEY,
                         user_id VARCHAR(100) NOT NULL,
                         account_number VARCHAR(50) NOT NULL UNIQUE,
                         balance NUMERIC(19, 4) NOT NULL DEFAULT 0.0000,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         version BIGINT NOT NULL DEFAULT 0,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabel Transactions (Mencatat header transaksi)
CREATE TABLE transactions (
                              id UUID PRIMARY KEY,
                              reference_no VARCHAR(100) NOT NULL UNIQUE,
                              sender_wallet_id UUID REFERENCES wallets(id),
                              receiver_wallet_id UUID REFERENCES wallets(id),
                              amount NUMERIC(19, 4) NOT NULL,
                              type VARCHAR(30) NOT NULL,
                              status VARCHAR(30) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabel Ledger Entries (Append-Only Double-Entry Bookkeeping)
CREATE TABLE ledger_entries (
                                id BIGSERIAL PRIMARY KEY,
                                transaction_id UUID NOT NULL REFERENCES transactions(id),
                                wallet_id UUID NOT NULL REFERENCES wallets(id),
                                entry_type VARCHAR(10) NOT NULL,
                                amount NUMERIC(19, 4) NOT NULL,
                                balance_after NUMERIC(19, 4) NOT NULL,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 4. Tabel Reconciliation Reports (Hasil Job Spring Batch EOD)
CREATE TABLE reconciliation_reports (
                                        id BIGSERIAL PRIMARY KEY,
                                        batch_date DATE NOT NULL,
                                        total_checked INT NOT NULL,
                                        matched_count INT NOT NULL,
                                        discrepancy_count INT NOT NULL,
                                        details JSONB,
                                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexing untuk mempercepat pencarian mutasi dan batch reconciliation
CREATE INDEX idx_ledger_wallet_created ON ledger_entries(wallet_id, created_at DESC);
CREATE INDEX idx_transactions_created_status ON transactions(created_at, status);