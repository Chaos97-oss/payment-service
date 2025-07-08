package com.example.paymentservice.repository

import com.example.paymentservice.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, String> {
    fun findByMerchantId(merchantId: String): List<Transaction>
}