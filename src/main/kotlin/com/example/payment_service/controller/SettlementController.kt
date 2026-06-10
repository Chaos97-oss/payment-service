package com.example.payment_service.controller

import com.example.payment_service.dto.res.SettlementBatchResponse
import com.example.payment_service.service.SettlementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SettlementController(
    private val settlementService: SettlementService
) {

    @PostMapping("/merchants/{merchantId}/settlements")
    fun settleMerchantTransactionsByPath(
        @PathVariable merchantId: String
    ): ResponseEntity<SettlementBatchResponse> {
        val batch = settlementService.settleMerchantTransactions(merchantId)
        return ResponseEntity.ok(batch)
    }

    @PostMapping("/settlements/settle")
    fun settleMerchantTransactions(
        @RequestParam merchantId: String
    ): ResponseEntity<SettlementBatchResponse> {
        val batch = settlementService.settleMerchantTransactions(merchantId)
        return ResponseEntity.ok(batch)
    }
}
