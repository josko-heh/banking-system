package com.josko.banking.bankingsystem.presentation.dto;

public record BasicAccountData(
		String iban,
		String type,
		Double balance
) {}
