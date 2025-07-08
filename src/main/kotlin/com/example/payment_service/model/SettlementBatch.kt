package com.example.payment_service.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class SettlementBatch(
    @Id
    val batchRef: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    val merchant: Merchant,

    val totalAmount: BigDecimal,

    val totalFee: BigDecimal,

    val numberOfTransactions: Int,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany
    @JoinTable(
        name = "settlement_batch_transactions",
        joinColumns = [JoinColumn(name = "batch_ref")],
        inverseJoinColumns = [JoinColumn(name = "transaction_ref")]
    )
    val transactions: List<Transaction>
)