package com.josko.banking.bankingsystem.presentation.dto;

public record TransactionCriteria(
		Double fromAmount,
		Double toAmount,
		String message
) {}
