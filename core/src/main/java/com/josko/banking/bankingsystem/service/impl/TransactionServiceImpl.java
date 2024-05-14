package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.persistence.repository.TransactionRepository;
import com.josko.banking.bankingsystem.service.RecordCreationService;
import com.josko.banking.bankingsystem.service.mapper.TransactionMapper;
import com.josko.banking.bankingsystem.service.record.TransactionRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(3)
@RequiredArgsConstructor
public class TransactionServiceImpl implements RecordCreationService<TransactionRecord> {
	
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
}
