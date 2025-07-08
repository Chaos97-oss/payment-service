package com.example.payment_service.repository

import com.example.payment_service.model.Transaction
import com.example.payment_service.model.Merchant
import com.example.payment_service.model.TransactionStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, String> {
    fun findAllByMerchantAndStatusAndCreatedAtBetween(
        merchant: Merchant,
        status: TransactionStatus,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<Transaction>

    fun findAllByMerchantAndStatus(merchant: Merchant, status: TransactionStatus): List<Transaction>
}
