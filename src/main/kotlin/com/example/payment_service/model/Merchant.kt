package com.example.paymentservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated


@Entity
data class Merchant(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val businessName: String,

    val email: String,

    val settlementAccount: String,

    @Enumerated(EnumType.STRING)
    var status: MerchantStatus = MerchantStatus.ACTIVE
)

enum class MerchantStatus {
    ACTIVE, INACTIVE
}