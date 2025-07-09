package com.example.payment_service.service

import com.example.payment_service.model.*
import com.example.payment_service.repository.MerchantRepository
import com.example.payment_service.repository.TransactionRepository
import com.example.payment_service.dto.req.InitiateTransactionRequest
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val merchantRepository: MerchantRepository,
    private val transactionRepository: TransactionRepository
) {

    fun initiateTransaction(req: InitiateTransactionRequest): Transaction {
    val merchant = merchantRepository.findById(req.merchantId)
        .orElseThrow { IllegalArgumentException("Merchant not found") }

    val feePercentage = BigDecimal("0.015")
    val maxFee = BigDecimal(200)

    var fee = req.amount.multiply(feePercentage)
    if (fee > maxFee) fee = maxFee

    val transaction = Transaction(
        merchantId = merchant.id,
        merchantRef = req.merchantRef,
        amount = req.amount,
        currency = req.currency,
        fee = fee,
        status = TransactionStatus.INITIATED,
        createdAt = LocalDateTime.now()
    )

    return transactionRepository.save(transaction)
}

    fun listTransactions(
        merchantId: String,
        status: TransactionStatus?,
        from: String?,
        to: String?
    ): List<Transaction> {
        val fromDate = from?.let { LocalDateTime.parse(it) } ?: LocalDateTime.MIN
        val toDate = to?.let { LocalDateTime.parse(it) } ?: LocalDateTime.MAX

        val transactions = transactionRepository.findAllByMerchantIdAndCreatedAtBetween(
            merchantId,
            fromDate,
            toDate
        )

        return if (status != null) {
            transactions.filter { it.status == status }
        } else {
            transactions
        }
    }
}