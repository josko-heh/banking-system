package com.josko.banking.bankingsystem.presentation.dto;

import java.util.List;

public record CustomerDetails(
		Long customerId,
		String name,
		String address,
		String email,
		String phoneNumber,
		Double balanceSum,
		List<BasicAccountData> accounts
) {}
