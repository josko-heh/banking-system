package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.repository.CustomerRepository;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import com.josko.banking.bankingsystem.service.mapper.CustomerMapper;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(0)
@RequiredArgsConstructor
public class CustomerServiceImpl implements RecordCreationService<CustomerRecord> {
	
	private final CustomerRepository repository;
	private final CustomerMapper mapper;

	
	@Override
	public Class<CustomerRecord> getType() {
		return CustomerRecord.class;
	}
	
	@Override
	public void create(CustomerRecord customerRecord) {
		repository.save(mapper.fromRecord(customerRecord));
	}
}
