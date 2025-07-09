package com.example.payment_service.service

import com.example.payment_service.dto.response.SettlementBatchResponse
// import com.example.payment_service.model.TransactionStatus
import com.example.payment_service.enums.TransactionStatus
import com.example.payment_service.model.SettlementBatch
import com.example.payment_service.repository.MerchantRepository
import com.example.payment_service.repository.SettlementBatchRepository
import com.example.payment_service.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.math.BigDecimal



@Service
class SettlementService(
    private val merchantRepository: MerchantRepository,
    private val transactionRepository: TransactionRepository,
    private val settlementBatchRepository: SettlementBatchRepository
) {

    fun settleMerchantTransactions(merchantId: String): SettlementBatchResponse {
    val merchant = merchantRepository.findById(merchantId)
        .orElseThrow { IllegalArgumentException("Merchant not found") }

    val transactions = transactionRepository.findAllByMerchantIdAndStatus(merchant.id, TransactionStatus.SUCCESS)

    if (transactions.isEmpty()) {
       
        return SettlementBatchResponse(
            batchRef = "",
            merchantId = merchant.id,
            totalAmount = BigDecimal.ZERO,
            feeDeducted = BigDecimal.ZERO,
            transactionCount = 0
        )
    }

    val totalAmount = transactions.fold(BigDecimal.ZERO) { acc, t -> acc + t.amount }
    val totalFee = transactions.fold(BigDecimal.ZERO) { acc, t -> acc + t.fee }
    val batch = SettlementBatch(
        id = UUID.randomUUID().toString(),
        merchant = merchant,
        totalAmount = totalAmount,
        transactions = transactions.toMutableList()
    )

    settlementBatchRepository.save(batch)

    return SettlementBatchResponse(
        batchRef = batch.id,
        merchantId = merchant.id,
        totalAmount = totalAmount,
        feeDeducted = totalFee,
        transactionCount = transactions.size
    )
}
}