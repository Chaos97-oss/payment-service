package com.example.payment_service.model
import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonIgnore
import com.example.payment_service.model.Transaction
import java.time.LocalDateTime
import java.math.BigDecimal
import java.util.UUID

@Entity
data class SettlementBatch(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val merchantId: String,

    val totalAmount: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany
    @JoinColumn(name = "batch_id") 
    @JsonIgnore
    val transactions: List<Transaction> = emptyList()
)