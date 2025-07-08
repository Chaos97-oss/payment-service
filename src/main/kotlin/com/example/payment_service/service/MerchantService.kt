package com.example.payment_service.service

import com.example.payment_service.dto.request.CreateMerchantRequest
import com.example.payment_service.dto.response.MerchantResponse
import com.example.payment_service.model.Merchant
import com.example.payment_service.repository.MerchantRepository
import org.springframework.stereotype.Service

@Service
class MerchantService(
    private val merchantRepository: MerchantRepository
) {
    fun createMerchant(request: CreateMerchantRequest): MerchantResponse {
        val merchant = Merchant(
            businessName = request.businessName,
            email = request.email,
            settlementAccount = request.settlementAccount
        )

        val saved = merchantRepository.save(merchant)

        return MerchantResponse(
            id = saved.id,
            businessName = saved.businessName,
            email = saved.email,
            settlementAccount = saved.settlementAccount,
            status = saved.status
        )
    }
}