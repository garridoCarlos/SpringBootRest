package com.technest.model;

import java.math.BigDecimal;

public class Transaction {
	
	private BigDecimal amount;
	private Long fromAccountId;
	private Long toAccountId;
	
	public Transaction() {}

	public Transaction(BigDecimal amount, Long fromAccountId, Long toAccountId) {
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

}
