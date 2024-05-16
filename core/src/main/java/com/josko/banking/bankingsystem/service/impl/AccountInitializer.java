package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.service.DataInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountInitializer implements DataInitializer {
	
	private final AccountRepository accountRepository;
	private final TurnoverUpdater turnoverUpdater;
	
	@Override
	public void initialize() {
		accountRepository.updateAccountBalances();
		turnoverUpdater.updateTurnovers();
	}
}
