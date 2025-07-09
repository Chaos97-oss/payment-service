package com.example.payment_service.repository

import com.example.payment_service.model.Transaction
import com.example.payment_service.model.TransactionStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, String> {
    fun findAllByMerchantIdAndStatus(
    merchantId: String,
    status: TransactionStatus
): List<Transaction>
    fun findAllByMerchantIdAndCreatedAtBetween(
        merchantId: String,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<Transaction>
}
