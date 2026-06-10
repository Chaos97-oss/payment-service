package com.example.payment_service.repository

import com.example.payment_service.enums.TransactionStatus
import com.example.payment_service.model.Transaction
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.time.LocalDateTime
import java.util.Optional

@Repository
class TransactionRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    private val mapper = RowMapper { rs, _ ->
        Transaction(
            id = rs.getString("id"),
            merchantId = rs.getString("merchant_id"),
            merchantRef = rs.getString("merchant_ref"),
            amount = rs.getBigDecimal("amount"),
            currency = rs.getString("currency"),
            fee = rs.getBigDecimal("fee"),
            internalRef = rs.getString("internal_ref"),
            status = TransactionStatus.valueOf(rs.getString("status")),
            settled = rs.getBoolean("settled"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime()
        )
    }

    fun save(transaction: Transaction): Transaction {
        jdbcTemplate.update(
            """
            INSERT INTO transactions (
                id, merchant_id, merchant_ref, amount, currency, fee,
                internal_ref, status, settled, created_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent(),
            transaction.id,
            transaction.merchantId,
            transaction.merchantRef,
            transaction.amount,
            transaction.currency,
            transaction.fee,
            transaction.internalRef,
            transaction.status.name,
            transaction.settled,
            transaction.createdAt
        )
        return transaction
    }

    fun findByMerchantIdAndMerchantRef(merchantId: String, merchantRef: String): Optional<Transaction> =
        jdbcTemplate.query(
            """
            SELECT id, merchant_id, merchant_ref, amount, currency, fee, internal_ref, status, settled, created_at
            FROM transactions
            WHERE merchant_id = ? AND merchant_ref = ?
            """.trimIndent(),
            mapper,
            merchantId,
            merchantRef
        ).firstOrNull().let { Optional.ofNullable(it) }

    fun findAllByMerchantIdAndCreatedAtBetween(
        merchantId: String,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<Transaction> =
        jdbcTemplate.query(
            """
            SELECT id, merchant_id, merchant_ref, amount, currency, fee, internal_ref, status, settled, created_at
            FROM transactions
            WHERE merchant_id = ? AND created_at BETWEEN ? AND ?
            ORDER BY created_at DESC
            """.trimIndent(),
            mapper,
            merchantId,
            from,
            to
        )

    fun findAllByMerchantId(merchantId: String): List<Transaction> =
        jdbcTemplate.query(
            """
            SELECT id, merchant_id, merchant_ref, amount, currency, fee, internal_ref, status, settled, created_at
            FROM transactions
            WHERE merchant_id = ?
            ORDER BY created_at DESC
            """.trimIndent(),
            mapper,
            merchantId
        )

    fun findUnsettledSuccessfulByMerchantId(merchantId: String): List<Transaction> =
        jdbcTemplate.query(
            """
            SELECT id, merchant_id, merchant_ref, amount, currency, fee, internal_ref, status, settled, created_at
            FROM transactions
            WHERE merchant_id = ? AND status = ? AND settled = false
            ORDER BY created_at ASC
            """.trimIndent(),
            mapper,
            merchantId,
            TransactionStatus.SUCCESS.name
        )

    fun markSettled(transactionIds: List<String>) {
        if (transactionIds.isEmpty()) return

        jdbcTemplate.batchUpdate(
            "UPDATE transactions SET settled = true WHERE id = ?",
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setString(1, transactionIds[i])
                }

                override fun getBatchSize(): Int = transactionIds.size
            }
        )
    }
}
