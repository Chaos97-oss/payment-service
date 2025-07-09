package com.example.payment_service.service

import com.example.payment_service.dto.request.CreateMerchantRequest
import com.example.payment_service.dto.response.MerchantResponse
import com.example.payment_service.model.Merchant
import com.example.payment_service.repository.MerchantRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MerchantService(
    private val merchantRepository: MerchantRepository
) {
    fun createMerchant(request: CreateMerchantRequest): MerchantResponse {
        val merchant = Merchant(
            id = UUID.randomUUID().toString(), // generate ID explicitly
            businessName = request.businessName,
            email = request.email,
            settlementAccount = request.settlementAccount,
            status = request.status
        )

        val savedMerchant = merchantRepository.save(merchant)

        return MerchantResponse.fromEntity(savedMerchant)
    }
}