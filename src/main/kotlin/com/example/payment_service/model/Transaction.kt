package com.example.payment_service.model

import com.example.payment_service.enums.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Transaction(

    val id: String = UUID.randomUUID().toString(),

    val merchantId: String = "",

    val merchantRef: String = "",

    val amount: BigDecimal = BigDecimal.ZERO,

    val currency: String = "",

    val fee: BigDecimal = BigDecimal.ZERO,

    val internalRef: String = UUID.randomUUID().toString(),

    val status: TransactionStatus = TransactionStatus.INITIATED,

    val settled: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
