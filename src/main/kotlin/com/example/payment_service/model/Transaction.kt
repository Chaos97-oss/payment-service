package com.example.payment_service.model

import com.example.payment_service.enums.TransactionStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "transactions")
class Transaction(

    @Id
    var id: String = UUID.randomUUID().toString(),

    var merchantId: String = "",

    var merchantRef: String = "",

    var amount: BigDecimal = BigDecimal.ZERO,

    var currency: String = "",

    var fee: BigDecimal = BigDecimal.ZERO,

    var internalRef: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.INITIATED,

    var settled: Boolean = false,

    var createdAt: LocalDateTime = LocalDateTime.now()

) {
    // No-arg constructor required by Hibernate
    constructor() : this(
        id = UUID.randomUUID().toString(),
        merchantId = "",
        merchantRef = "",
        amount = BigDecimal.ZERO,
        currency = "",
        fee = BigDecimal.ZERO,
        internalRef = UUID.randomUUID().toString(),
        status = TransactionStatus.INITIATED,
        settled = false,
        createdAt = LocalDateTime.now()
    )
}
