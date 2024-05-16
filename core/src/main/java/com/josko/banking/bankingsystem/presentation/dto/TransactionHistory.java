package com.josko.banking.bankingsystem.presentation.dto;

import java.util.List;

public record TransactionHistory(
		List<TransactionDTO> sent,
		List<TransactionDTO> received
) {}
