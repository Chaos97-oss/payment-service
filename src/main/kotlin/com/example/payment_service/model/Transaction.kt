package com.example.payment_service.model

import com.example.payment_service.enums.TransactionStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false)
    val merchantRef: String,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val currency: String,

    @Column(nullable = false)
    val fee: BigDecimal,

    @Column(nullable = false)
    val internalRef: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TransactionStatus = TransactionStatus.INITIATED,

    var settled: Boolean = false,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)