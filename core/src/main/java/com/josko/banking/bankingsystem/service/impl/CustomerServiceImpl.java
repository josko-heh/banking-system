package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.repository.CustomerRepository;
import com.josko.banking.bankingsystem.presentation.dto.BasicAccountData;
import com.josko.banking.bankingsystem.presentation.dto.CustomerDetails;
import com.josko.banking.bankingsystem.service.CustomerService;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import com.josko.banking.bankingsystem.service.mapper.AccountMapper;
import com.josko.banking.bankingsystem.service.mapper.CustomerMapper;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.of;

@Service
@Order(0)
@RequiredArgsConstructor
public class CustomerServiceImpl implements RecordCreationService<CustomerRecord>, CustomerService {
	
	private final CustomerRepository repository;
	private final CustomerMapper mapper;
	private final AccountMapper accountMapper;

	
	@Override
	public Class<CustomerRecord> getType() {
		return CustomerRecord.class;
	}
	
	@Override
	public void create(CustomerRecord customerRecord) {
		repository.save(mapper.fromRecord(customerRecord));
	}

	@Override
	public Optional<CustomerDetails> getCustomerDetails(Long customerId) {
		var customerOptional = repository.findById(customerId);

		if (customerOptional.isEmpty())
			return Optional.empty();
		
		var customer = customerOptional.get();
		var basicAccounts = accountMapper.toBasicDataList(customer.getAccounts());
		var balanceSum = basicAccounts.stream()
				.mapToDouble(BasicAccountData::balance)
				.sum();
				
		return of(mapper.toDetails(customer, balanceSum, basicAccounts));
	}
}
