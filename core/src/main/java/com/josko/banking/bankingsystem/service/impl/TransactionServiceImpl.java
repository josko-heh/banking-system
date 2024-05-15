package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.persistence.repository.TransactionRepository;
import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import com.josko.banking.bankingsystem.service.TransactionService;
import com.josko.banking.bankingsystem.service.mapper.TransactionMapper;
import com.josko.banking.bankingsystem.service.record.TransactionRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.of;

@Service
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements RecordCreationService<TransactionRecord>, TransactionService {
	
	private final TransactionRepository repository;
	private final TransactionMapper mapper;
	private final AccountRepository accountRepository;

	
	@Override
	public Class<TransactionRecord> getType() {
		return TransactionRecord.class;
	}
	
	@Override
	@Transactional
	@Async
	public void create(TransactionRecord transactionRecord) {
		var senderAcc = accountRepository.findById(transactionRecord.getSenderAccountId()).orElseThrow();
		var receiverAcc = accountRepository.findById(transactionRecord.getReceiverAccountId()).orElseThrow();

		Transaction transaction = mapper.fromRecord(transactionRecord);
		transaction.setSenderAccount(senderAcc);
		transaction.setReceiverAccount(receiverAcc);
		
		repository.save(transaction);
	}

	@Override
	@Transactional
	public Optional<Long> create(TransactionDTO dto) {
		try {
			Transaction entity = mapper.fromDTO(dto);
			entity.setReceiverAccount(accountRepository.findById(dto.receiverAccountId()).orElseThrow());
			entity.setSenderAccount(accountRepository.findById(dto.senderAccountId()).orElseThrow());
			
			var created = repository.save(entity);

			updateBalance(created, dto.amount());

			return of(created.getTransactionId());
		} catch (RuntimeException e) {
			log.error("Failed to create a transaction.", e);
			return Optional.empty();
		}
	}

	private static void updateBalance(Transaction transaction, Double amount) {
		Account receiverAccount = transaction.getReceiverAccount();
		Account senderAccount = transaction.getSenderAccount();

		receiverAccount.setBalance(receiverAccount.getBalance() + amount);
		senderAccount.setBalance(senderAccount.getBalance() - amount);
	}
}
