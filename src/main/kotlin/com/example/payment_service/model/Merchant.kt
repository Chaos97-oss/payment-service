package com.example.payment_service.model

import com.example.payment_service.enums.MerchantStatus
import jakarta.persistence.*

@Entity
@Table(name = "merchants")
class Merchant(
    @Id
    var id: String? = null,

    var businessName: String? = null,

    var email: String? = null,

    var settlementAccount: String? = null,

    @Enumerated(EnumType.STRING)
    var status: MerchantStatus? = null
) {
    constructor() : this(null, null, null, null, null)
}