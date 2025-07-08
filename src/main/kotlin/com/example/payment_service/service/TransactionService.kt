package com.example.payment_service.service

import com.example.payment_service.dto.request.InitiateTransactionRequest
import com.example.payment_service.dto.response.TransactionResponse
import com.example.payment_service.model.Transaction
import com.example.payment_service.model.TransactionStatus
import com.example.payment_service.repository.MerchantRepository
import com.example.payment_service.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val merchantRepository: MerchantRepository,
    private val transactionRepository: TransactionRepository
) {
    fun initiateTransaction(request: InitiateTransactionRequest): TransactionResponse {
        val merchant = merchantRepository.findById(request.merchantId)
            .orElseThrow { IllegalArgumentException("Merchant not found") }

        val feePercentage = BigDecimal("0.015") // 1.5%
        val maxFee = BigDecimal(200)

        var fee = request.amount.multiply(feePercentage)
        if (fee > maxFee) fee = maxFee

        val transaction = Transaction(
            merchantId = merchant.id,
            merchantRef = request.merchantRef,
            internalRef = java.util.UUID.randomUUID().toString(),
            amount = request.amount,
            currency = request.currency,
            fee = fee,
            status = TransactionStatus.SUCCESS,
            createdAt = LocalDateTime.now())

        val saved = transactionRepository.save(transaction)

        return TransactionResponse(
            id = saved.id,
            merchantId = saved.merchantId,
            merchantRef = saved.merchantRef,
            internalRef = saved.internalRef,
            amount = saved.amount,
            currency = saved.currency,
            fee = saved.fee,
            status = saved.status,
            createdAt = saved.createdAt
        )
    }

    fun listTransactions(
        merchantId: String,
        status: TransactionStatus?,
        from: String?,
        to: String?
    ): List<TransactionResponse> {
        val merchant = merchantRepository.findById(merchantId)
            .orElseThrow { IllegalArgumentException("Merchant not found") }

        val fromDate = from?.let { LocalDateTime.parse(it) } ?: LocalDateTime.MIN
        val toDate = to?.let { LocalDateTime.parse(it) } ?: LocalDateTime.MAX

        val transactions = if (status != null) {
            transactionRepository.findAllByMerchantAndStatusAndCreatedAtBetween(
                merchant, status, fromDate, toDate
            )
        } else {
            transactionRepository.findAllByMerchantAndStatusAndCreatedAtBetween(
                merchant, TransactionStatus.SUCCESS, fromDate, toDate
            )
        }

        return transactions.map {
            TransactionResponse(
                id = it.id,
                merchantId = it.merchantId,
                merchantRef = it.merchantRef,
                internalRef = it.internalRef,
                amount = it.amount,
                currency = it.currency,
                fee = it.fee,
                status = it.status,
                createdAt = it.createdAt
            )
        }
    }
}