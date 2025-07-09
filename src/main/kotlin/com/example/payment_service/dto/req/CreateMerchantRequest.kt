package com.example.payment_service.dto.request
import com.example.payment_service.enums.MerchantStatus

data class CreateMerchantRequest(
    val businessName: String,
    val email: String,
    val settlementAccount: String,
    val status: MerchantStatus
)
