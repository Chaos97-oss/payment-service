package com.example.payment_service.model

import java.math.BigDecimal
import java.util.UUID

data class SettlementBatch(

    val batchRef: String = UUID.randomUUID().toString(),

    val merchantId: String = "",

    val totalAmount: BigDecimal = BigDecimal.ZERO,

    val transactions: List<Transaction> = emptyList()
)
