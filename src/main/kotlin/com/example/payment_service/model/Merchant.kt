package com.example.payment_service.model

import com.example.payment_service.enums.MerchantStatus
import java.util.UUID

data class Merchant(
    val id: String = UUID.randomUUID().toString(),

    val businessName: String = "",

    val email: String = "",

    val settlementAccount: String = "",

    val status: MerchantStatus = MerchantStatus.INACTIVE
)
