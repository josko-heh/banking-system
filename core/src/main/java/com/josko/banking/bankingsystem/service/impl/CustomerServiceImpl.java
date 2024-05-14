package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.entity.Customer;
import com.josko.banking.bankingsystem.persistence.repository.CustomerRepository;
import com.josko.banking.bankingsystem.service.CustomerService;
import com.josko.banking.bankingsystem.service.mapper.CustomerMapper;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository repository;
	private final CustomerMapper mapper;
	
	@Override
	// todo async
	public Customer create(CustomerRecord customerRecord) {
		return repository.save(mapper.fromRecord(customerRecord));
	}
}
