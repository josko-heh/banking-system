package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.service.DataInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceInitializer implements DataInitializer {
	
	private final AccountRepository accountRepository;
	
	@Override
	public void initialize() {
		accountRepository.updateAccountBalances();
	}
}
