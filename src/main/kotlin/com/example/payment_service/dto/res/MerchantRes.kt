package com.example.payment_service.dto.response

import com.example.payment_service.model.Merchant
import com.example.payment_service.enums.MerchantStatus

data class MerchantResponse(
    val id: String,
    val businessName: String,
    val email: String,
    val settlementAccount: String,
    val status: MerchantStatus
) {
    companion object {
        fun fromEntity(merchant: Merchant): MerchantResponse {
            return MerchantResponse(
                id = merchant.id,
                businessName = merchant.businessName,
                email = merchant.email,
                settlementAccount = merchant.settlementAccount,
                status = merchant.status
            )
        }
    }
}