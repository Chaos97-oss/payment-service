package com.example.payment_service.dto.request

data class CreateMerchantRequest(
    val businessName: String,
    val email: String,
    val settlementAccount: String
)