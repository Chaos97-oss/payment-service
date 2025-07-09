package com.example.payment_service.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "settlement_batches")
class SettlementBatch(

    @Id
    var id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    var merchant: Merchant = Merchant(),  

    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "settlement_batch_transactions",
        joinColumns = [JoinColumn(name = "batch_id")],
        inverseJoinColumns = [JoinColumn(name = "transaction_id")]
    )
    var transactions: MutableList<Transaction> = mutableListOf()
) {
    constructor() : this(
        id = UUID.randomUUID().toString(),
        merchant = Merchant(),      
        totalAmount = BigDecimal.ZERO,
        transactions = mutableListOf()
    )
}
