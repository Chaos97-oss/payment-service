package com.example.payment_service.repository

import com.example.payment_service.enums.MerchantStatus
import com.example.payment_service.model.Merchant
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MerchantRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    private val mapper = RowMapper { rs, _ ->
        Merchant(
            id = rs.getString("id"),
            businessName = rs.getString("business_name"),
            email = rs.getString("email"),
            settlementAccount = rs.getString("settlement_account"),
            status = MerchantStatus.valueOf(rs.getString("status"))
        )
    }

    fun save(merchant: Merchant): Merchant {
        jdbcTemplate.update(
            """
            INSERT INTO merchants (id, business_name, email, settlement_account, status)
            VALUES (?, ?, ?, ?, ?)
            """.trimIndent(),
            merchant.id,
            merchant.businessName,
            merchant.email,
            merchant.settlementAccount,
            merchant.status.name
        )
        return merchant
    }

    fun findById(id: String): Optional<Merchant> =
        jdbcTemplate.query(
            """
            SELECT id, business_name, email, settlement_account, status
            FROM merchants
            WHERE id = ?
            """.trimIndent(),
            mapper,
            id
        ).firstOrNull().let { Optional.ofNullable(it) }

    fun findByEmail(email: String): Optional<Merchant> =
        jdbcTemplate.query(
            """
            SELECT id, business_name, email, settlement_account, status
            FROM merchants
            WHERE email = ?
            """.trimIndent(),
            mapper,
            email
        ).firstOrNull().let { Optional.ofNullable(it) }
}
