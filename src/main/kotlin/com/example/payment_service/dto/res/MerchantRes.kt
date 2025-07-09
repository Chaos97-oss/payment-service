package com.example.payment_service.dto.response


import com.example.payment_service.enums.MerchantStatus

data class MerchantResponse(
    val id: String,
    val businessName: String,
    val email: String,
    val settlementAccount: String,
    val status: MerchantStatus
)