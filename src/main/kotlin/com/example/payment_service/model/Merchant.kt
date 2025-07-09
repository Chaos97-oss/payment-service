package com.example.payment_service.model

import com.example.payment_service.enums.MerchantStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.util.*

@Entity
data class Merchant(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val businessName: String,

    val email: String,

    val settlementAccount: String,

    @Enumerated(EnumType.STRING)
    val status: MerchantStatus = MerchantStatus.ACTIVE
)
