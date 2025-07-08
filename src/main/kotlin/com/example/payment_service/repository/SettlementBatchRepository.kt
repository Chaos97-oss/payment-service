package com.example.paymentservice.repository

import com.example.paymentservice.model.SettlementBatch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SettlementBatchRepository : JpaRepository<SettlementBatch, String>