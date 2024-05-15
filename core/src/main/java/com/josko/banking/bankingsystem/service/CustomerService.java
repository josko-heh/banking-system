package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.presentation.dto.CustomerDetails;

import java.util.Optional;

public interface CustomerService {

	Optional<CustomerDetails> getCustomerDetails(Long customerId);
}
