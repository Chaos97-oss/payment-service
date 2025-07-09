package com.example.payment_service.dto.response

import com.example.payment_service.enums.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionResponse(
    val id: String,
    val merchantId: String,
    val merchantRef: String,
    val internalRef: String,
    val amount: BigDecimal,
    val currency: String,
    val fee: BigDecimal,
    val status: TransactionStatus,
    val createdAt: LocalDateTime
)