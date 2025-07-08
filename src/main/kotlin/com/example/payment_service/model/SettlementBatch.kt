package com.example.paymentservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class SettlementBatch(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val merchantId: String,

    val batchRef: String,

    val totalAmount: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now()
)