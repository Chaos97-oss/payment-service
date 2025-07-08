package com.example.payment_service.controller

import com.example.payment_service.dto.request.InitiateTransactionRequest
import com.example.payment_service.dto.response.TransactionResponse
import com.example.payment_service.model.TransactionStatus
import com.example.payment_service.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping("/initiate")
    fun initiateTransaction(
        @RequestBody request: InitiateTransactionRequest
    ): ResponseEntity<TransactionResponse> {
        val tx = transactionService.initiateTransaction(request)
        return ResponseEntity.ok(tx)
    }

    @GetMapping
    fun listTransactions(
        @RequestParam merchantId: String,
        @RequestParam(required = false) status: TransactionStatus?,
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?
    ): ResponseEntity<List<TransactionResponse>> {
        val txs = transactionService.listTransactions(merchantId, status, from, to)
        return ResponseEntity.ok(txs)
    }
}