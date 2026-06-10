package com.example.payment_service.service

import com.example.payment_service.dto.req.InitiateTransactionRequest
import com.example.payment_service.enums.TransactionStatus
import com.example.payment_service.model.Transaction
import com.example.payment_service.repository.MerchantRepository
import com.example.payment_service.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.UUID

@Service
class TransactionService(
    private val merchantRepository: MerchantRepository,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun initiateTransaction(req: InitiateTransactionRequest): Transaction {
        if (req.amount <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Transaction amount must be greater than zero")
        }

        val merchant = merchantRepository.findById(req.merchantId)
            .orElseThrow { IllegalArgumentException("Merchant not found") }

        val existingTransaction = transactionRepository.findByMerchantIdAndMerchantRef(merchant.id, req.merchantRef)
        if (existingTransaction.isPresent) {
            return existingTransaction.get()
        }

        val transaction = Transaction(
            merchantId = merchant.id,
            merchantRef = req.merchantRef,
            internalRef = UUID.randomUUID().toString(),
            amount = req.amount.setScale(2, RoundingMode.HALF_UP),
            currency = req.currency.uppercase(),
            fee = calculateFee(req.amount),
            status = TransactionStatus.SUCCESS,
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
        val fromDate = from?.let { LocalDateTime.parse(it) }
        val toDate = to?.let { LocalDateTime.parse(it) }

        val transactions = when {
            fromDate != null && toDate != null -> transactionRepository.findAllByMerchantIdAndCreatedAtBetween(merchantId, fromDate, toDate)
            else -> transactionRepository.findAllByMerchantId(merchantId)
        }

        return if (status != null) {
            transactions.filter { it.status == status }
        } else {
            transactions
        }
    }

    private fun calculateFee(amount: BigDecimal): BigDecimal {
        val feePercentage = BigDecimal("0.015")
        val maxFee = BigDecimal("200.00")
        val calculatedFee = amount.multiply(feePercentage).setScale(2, RoundingMode.HALF_UP)

        return calculatedFee.min(maxFee)
    }
}
