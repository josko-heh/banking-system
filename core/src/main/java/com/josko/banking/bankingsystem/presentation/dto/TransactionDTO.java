package com.josko.banking.bankingsystem.presentation.dto;

import java.time.Instant;

public record TransactionDTO (
		Long transactionId,
		Double amount,
		String message,
		Instant timestamp,
		Long senderAccountId,
		Long receiverAccountId
) {}
