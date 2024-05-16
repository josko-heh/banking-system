package com.josko.banking.bankingsystem.persistence.repository.criteria;

public record TransactionRepoCriteria(
		Double fromAmount,
		Double toAmount,
		String message,
		Long senderId,
		Long receiverId
) {}
