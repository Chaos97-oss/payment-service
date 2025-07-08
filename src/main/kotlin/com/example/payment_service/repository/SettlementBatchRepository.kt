package com.example.payment_service.repository

import com.example.payment_service.model.SettlementBatch
import org.springframework.data.jpa.repository.JpaRepository

interface SettlementBatchRepository : JpaRepository<SettlementBatch, String>
