package com.example.payment_service.mapper

import com.example.payment_service.dto.response.TransactionResponse
import com.example.payment_service.model.Transaction

fun Transaction.toResponse(): TransactionResponse =
    TransactionResponse(
        id = this.id,
        merchantId = this.merchantId,
        merchantRef = this.merchantRef,
        internalRef = this.internalRef,
        amount = this.amount,
        currency = this.currency,
        fee = this.fee,
        status = this.status,
        createdAt = this.createdAt
    )