package com.example.payment_service.model

import com.example.payment_service.enums.MerchantStatus
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "merchants")
class Merchant(
    @Id
    var id: String = UUID.randomUUID().toString(),

    var businessName: String = "",

    var email: String = "",

    var settlementAccount: String = "",

    @Enumerated(EnumType.STRING)
    var status: MerchantStatus = MerchantStatus.INACTIVE
) {
    // No need for explicit no-args constructor now, default values handle that
}