package com.example.payment_service.controller

import com.example.payment_service.dto.request.CreateMerchantRequest
import com.example.payment_service.dto.response.MerchantResponse
import com.example.payment_service.service.MerchantService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/merchants")
class MerchantController(
    private val merchantService: MerchantService
) {
    @PostMapping
    fun createMerchant(
        @RequestBody request: CreateMerchantRequest
    ): ResponseEntity<MerchantResponse> {
        val merchant = merchantService.createMerchant(request)
        return ResponseEntity.ok(merchant)
    }
}