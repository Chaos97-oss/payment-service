package com.example.payment_service.repository

import com.example.payment_service.model.Merchant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MerchantRepository : JpaRepository<Merchant, String> {
    fun findByEmail(email: String): Optional<Merchant>
}