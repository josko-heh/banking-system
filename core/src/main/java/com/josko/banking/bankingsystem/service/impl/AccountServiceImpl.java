package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.persistence.repository.CustomerRepository;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import com.josko.banking.bankingsystem.service.mapper.AccountMapper;
import com.josko.banking.bankingsystem.service.record.AccountRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(1)
@RequiredArgsConstructor
public class AccountServiceImpl implements RecordCreationService<AccountRecord> {
	
	private final AccountRepository repository;
	private final AccountMapper mapper;
	private final CustomerRepository customerRepository;

	@Override
	public Class<AccountRecord> getType() {
		return AccountRecord.class;
	}

	@Override
	@Transactional
	public void create(AccountRecord accountRecord) {
		var customer = customerRepository.findById(accountRecord.getCustomerId()).orElseThrow();
		
		Account account = mapper.fromRecord(accountRecord);
		account.setCustomer(customer);
		
		repository.save(account);
	}
}
