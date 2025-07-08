package com.example.paymentservice.model

import jakarta.persistence.Entity
import jakarta.persistence.IdClass
import jakarta.persistence.Id

@Entity
@IdClass(SettlementBatchTransactionId::class)
data class SettlementBatchTransactions(
    @Id
    val batchId: String,

    @Id
    val transactionId: String
)

data class SettlementBatchTransactionId(
    val batchId: String = "",
    val transactionId: String = ""
) : java.io.Serializable
