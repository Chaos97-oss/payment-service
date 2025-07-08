package com.example.payment_service.dto.request

import java.math.BigDecimal

data class InitiateTransactionRequest(
    val merchantId: String,
    val merchantRef: String,
    val amount: BigDecimal,
    val currency: String
)