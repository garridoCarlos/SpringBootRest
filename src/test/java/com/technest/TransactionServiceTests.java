package com.technest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.technest.repository.AccountRepository;
import com.technest.controller.ITransactionService;
import com.technest.controller.TransactionService;
import com.technest.model.Account;

public class TransactionServiceTests {

	private Account senderAccount = new Account("Account1", Currency.getInstance("EUR"), new BigDecimal("20.00"),
			false);
	private Account recipientAccount = new Account("Account2", Currency.getInstance("EUR"), new BigDecimal("20.00"),
			false);
	private Account recipientAccount2 = new Account("Account3", Currency.getInstance("GBP"), new BigDecimal("20.00"),
			false);

	@Autowired
	AccountRepository repository;

	ITransactionService transactionService = new TransactionService(repository);

	// Integration Test

	@Test
	void hasAmount_ValidAmount_ReturnsTrue() {
		// Arrange
		boolean hasAmount = false;

		// Act
		hasAmount = ((TransactionService) transactionService).hasAmount(senderAccount.getBalance(),
				new BigDecimal("10.00"));

		// Assert
		assertEquals(true, hasAmount);
	}

	@Test
	void hasAmount_InvalidAmount_ReturnsFalse() {
		// Arrange
		boolean hasAmount = false;

		// Act
		hasAmount = ((TransactionService) transactionService).hasAmount(senderAccount.getBalance(),
				new BigDecimal("50.00"));

		// Assert
		assertEquals(false, hasAmount);
	}

	@Test
	void sameCurrency_isSame_ReturnsTrue() {
		// Arrange
		boolean sameCurrency = true;

		// Act
		sameCurrency = ((TransactionService) transactionService).sameCurrency(senderAccount.getCurrency(),
				recipientAccount.getCurrency());

		// Assert
		assertEquals(true, sameCurrency);
	}

	@Test
	void sameCurrency_isDifferent_ReturnsFalse() {
		// Arrange
		boolean sameCurrency = false;

		// Act
		sameCurrency = ((TransactionService) transactionService).sameCurrency(senderAccount.getCurrency(),
				recipientAccount2.getCurrency());

		// Assert
		assertEquals(false, sameCurrency);
	}

}
