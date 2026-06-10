CREATE TABLE IF NOT EXISTS merchants (
    id VARCHAR(36) PRIMARY KEY,
    business_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    settlement_account VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(36) PRIMARY KEY,
    merchant_id VARCHAR(36) NOT NULL,
    merchant_ref VARCHAR(100) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    fee DECIMAL(19, 2) NOT NULL,
    internal_ref VARCHAR(36) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    settled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_transactions_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(id),
    CONSTRAINT uk_transactions_merchant_ref
        UNIQUE (merchant_id, merchant_ref)
);

CREATE TABLE IF NOT EXISTS settlement_batches (
    batch_ref VARCHAR(36) PRIMARY KEY,
    merchant_id VARCHAR(36) NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_settlement_batches_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE IF NOT EXISTS settlement_batch_transactions (
    batch_ref VARCHAR(36) NOT NULL,
    transaction_id VARCHAR(36) NOT NULL UNIQUE,
    PRIMARY KEY (batch_ref, transaction_id),
    CONSTRAINT fk_batch_transactions_batch
        FOREIGN KEY (batch_ref) REFERENCES settlement_batches(batch_ref),
    CONSTRAINT fk_batch_transactions_transaction
        FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);
