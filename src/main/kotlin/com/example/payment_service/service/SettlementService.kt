package com.example.payment_service.service

import com.example.payment_service.dto.res.SettlementBatchResponse
import com.example.payment_service.model.SettlementBatch
import com.example.payment_service.repository.MerchantRepository
import com.example.payment_service.repository.SettlementBatchRepository
import com.example.payment_service.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class SettlementService(
    private val merchantRepository: MerchantRepository,
    private val transactionRepository: TransactionRepository,
    private val settlementBatchRepository: SettlementBatchRepository
) {

    @Transactional
    fun settleMerchantTransactions(merchantId: String): SettlementBatchResponse {
        val merchant = merchantRepository.findById(merchantId)
            .orElseThrow { IllegalArgumentException("Merchant not found") }

        val transactions = transactionRepository.findUnsettledSuccessfulByMerchantId(merchant.id)

        if (transactions.isEmpty()) {
            return SettlementBatchResponse(
                batchRef = "",
                merchantId = merchant.id,
                totalAmount = BigDecimal.ZERO,
                feeDeducted = BigDecimal.ZERO,
                transactionCount = 0
            )
        }

        val grossAmount = transactions.fold(BigDecimal.ZERO) { acc, transaction -> acc + transaction.amount }
        val totalFee = transactions.fold(BigDecimal.ZERO) { acc, transaction -> acc + transaction.fee }
        val netSettlementAmount = grossAmount - totalFee

        val batch = SettlementBatch(
            merchantId = merchant.id,
            totalAmount = netSettlementAmount,
            transactions = transactions
        )

        settlementBatchRepository.save(batch)
        transactionRepository.markSettled(transactions.map { it.id })

        return SettlementBatchResponse(
            batchRef = batch.batchRef,
            merchantId = merchant.id,
            totalAmount = netSettlementAmount,
            feeDeducted = totalFee,
            transactionCount = transactions.size
        )
    }
}
