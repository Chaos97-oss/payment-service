package com.example.payment_service.controller

import com.example.payment_service.dto.response.SettlementBatchResponse
import com.example.payment_service.service.SettlementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/settlements")
class SettlementController(
    private val settlementService: SettlementService
) {

    @PostMapping("/settle")
    fun settleMerchantTransactions(
        @RequestParam merchantId: String
    ): ResponseEntity<SettlementBatchResponse> {
        val batch = settlementService.settleMerchantTransactions(merchantId)
        return ResponseEntity.ok(batch)
    }
}