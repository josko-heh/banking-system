package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.presentation.dto.TransactionHistory;

import java.util.Optional;

public interface TransactionService {

	Optional<Long> create(TransactionDTO transactionDTO);

	Optional<TransactionHistory> getHistory(Long customerId);
}
