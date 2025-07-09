package com.example.payment_service.dto.response

import java.math.BigDecimal

data class SettlementBatchResponse(
    val batchRef: String,
    val merchantId: String,
    val totalAmount: BigDecimal,
    val feeDeducted: BigDecimal,
    val transactionCount: Int
)