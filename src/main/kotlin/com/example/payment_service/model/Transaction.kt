package com.example.payment_service.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import com.example.payment_service.enums.TransactionStatus
@Entity
@Table(name = "transactions")
class Transaction(

    @Id
    var id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    var merchant: Merchant? = null,

    var merchantRef: String? = null,

    var amount: BigDecimal? = null,

    var currency: String? = null,

    var fee: BigDecimal? = null,

    var internalRef: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.INITIATED,

    var settled: Boolean = false,

    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        id = "",
        merchant = null,
        merchantRef = null,
        amount = null,
        currency = null,
        fee = null,
        internalRef = "",
        status = TransactionStatus.INITIATED,
        settled = false,
        createdAt = LocalDateTime.now()
    )
}