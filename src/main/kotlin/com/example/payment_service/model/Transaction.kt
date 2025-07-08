package com.example.payment_service.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Transaction(
    @Id
    val internalRef: String = UUID.randomUUID().toString(),

    val merchantRef: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    val merchant: Merchant,

    val amount: BigDecimal,

    val currency: String,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.INITIATED,

    val fee: BigDecimal = BigDecimal.ZERO,

    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class TransactionStatus {
    INITIATED, SUCCESS, FAILED
}