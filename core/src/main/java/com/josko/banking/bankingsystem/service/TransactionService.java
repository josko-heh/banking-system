package com.josko.banking.bankingsystem.service;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;

import java.util.Optional;

public interface TransactionService {

	Optional<Long> create(TransactionDTO transactionDTO);
}
