package com.example.payment_service.repository

import com.example.payment_service.model.SettlementBatch
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class SettlementBatchRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun save(batch: SettlementBatch): SettlementBatch {
        jdbcTemplate.update(
            """
            INSERT INTO settlement_batches (batch_ref, merchant_id, total_amount)
            VALUES (?, ?, ?)
            """.trimIndent(),
            batch.batchRef,
            batch.merchantId,
            batch.totalAmount
        )

        jdbcTemplate.batchUpdate(
            """
            INSERT INTO settlement_batch_transactions (batch_ref, transaction_id)
            VALUES (?, ?)
            """.trimIndent(),
            object : BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    ps.setString(1, batch.batchRef)
                    ps.setString(2, batch.transactions[i].id)
                }

                override fun getBatchSize(): Int = batch.transactions.size
            }
        )

        return batch
    }
}
