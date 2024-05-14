package com.josko.banking.bankingsystem.service.record;


public record CustomerRecord(
		Long customerId,
		String name,
		String address,
		String email,
		String phoneNumber
) {}
