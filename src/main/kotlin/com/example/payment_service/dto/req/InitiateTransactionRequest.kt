package com.example.payment_service.dto.req

import java.math.BigDecimal

data class InitiateTransactionRequest(
    val merchantId: String,
    val merchantRef: String,
    val amount: BigDecimal,
    val currency: String
)