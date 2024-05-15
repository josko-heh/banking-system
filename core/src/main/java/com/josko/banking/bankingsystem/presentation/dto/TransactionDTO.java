package com.josko.banking.bankingsystem.presentation.dto;

public record TransactionDTO (
		Double amount,
		String message,
		Long senderAccountId,
		Long receiverAccountId
) {}
