package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.persistence.entity.Customer;
import com.josko.banking.bankingsystem.service.record.CustomerRecord;

public interface CustomerService {

	public Customer create(CustomerRecord customerRecord);
}
