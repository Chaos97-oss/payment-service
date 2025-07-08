package com.example.paymentservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Transaction(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val merchantId: String,

    val merchantRef: String,

    val internalRef: String,

    val amount: BigDecimal,

    val currency: String,

    val fee: BigDecimal,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.INITIATED,

    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class TransactionStatus {
    INITIATED, SUCCESS, FAILED
}