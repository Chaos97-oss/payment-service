package com.example.payment_service

import com.example.payment_service.dto.req.CreateMerchantRequest
import com.example.payment_service.dto.req.InitiateTransactionRequest
import com.example.payment_service.enums.MerchantStatus
import com.example.payment_service.enums.TransactionStatus
import com.example.payment_service.service.MerchantService
import com.example.payment_service.service.SettlementService
import com.example.payment_service.service.TransactionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest(
	properties = [
		"spring.datasource.url=jdbc:h2:mem:payment_service_test;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.sql.init.mode=always"
	]
)
class PaymentServiceApplicationTests @Autowired constructor(
	private val merchantService: MerchantService,
	private val transactionService: TransactionService,
	private val settlementService: SettlementService
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `initiates idempotent transaction and settles it once`() {
		val merchant = merchantService.createMerchant(
			CreateMerchantRequest(
				businessName = "Acme Stores",
				email = "settlement-test@example.com",
				settlementAccount = "0123456789",
				status = MerchantStatus.ACTIVE
			)
		)

		val request = InitiateTransactionRequest(
			merchantId = merchant.id,
			merchantRef = "ORDER-1001",
			amount = BigDecimal("10000.00"),
			currency = "ngn"
		)

		val transaction = transactionService.initiateTransaction(request)
		val duplicate = transactionService.initiateTransaction(request)
		val batch = settlementService.settleMerchantTransactions(merchant.id)
		val emptyBatch = settlementService.settleMerchantTransactions(merchant.id)

		assertThat(transaction.status).isEqualTo(TransactionStatus.SUCCESS)
		assertThat(transaction.currency).isEqualTo("NGN")
		assertThat(transaction.fee).isEqualByComparingTo("150.00")
		assertThat(duplicate.id).isEqualTo(transaction.id)
		assertThat(batch.transactionCount).isEqualTo(1)
		assertThat(batch.totalAmount).isEqualByComparingTo("9850.00")
		assertThat(batch.feeDeducted).isEqualByComparingTo("150.00")
		assertThat(emptyBatch.transactionCount).isZero()
	}
}
