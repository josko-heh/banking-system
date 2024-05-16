package com.josko.banking.bankingsystem.service.impl;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.persistence.repository.AccountRepository;
import com.josko.banking.bankingsystem.persistence.repository.CustomerRepository;
import com.josko.banking.bankingsystem.persistence.repository.TransactionRepository;
import com.josko.banking.bankingsystem.persistence.repository.criteria.TransactionRepoCriteria;
import com.josko.banking.bankingsystem.presentation.dto.TransactionCriteria;
import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.presentation.dto.TransactionHistory;
import com.josko.banking.bankingsystem.service.EmailService;
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

import java.util.List;
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
	private final CustomerRepository customerRepository;
	private final EmailService emailService;

	
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
		Account receiverAccount = null;
		Account senderAccount = null;
		Double oldReceiverBalance = null;
		Double oldSenderBalance = null;

		try {
			receiverAccount = accountRepository.findById(dto.receiverAccountId()).orElseThrow();
			senderAccount = accountRepository.findById(dto.senderAccountId()).orElseThrow();

			Transaction entity = mapper.fromDTO(dto);
			entity.setReceiverAccount(receiverAccount);
			entity.setSenderAccount(senderAccount);

			var created = repository.save(entity);

			oldReceiverBalance = receiverAccount.getBalance();
			oldSenderBalance = senderAccount.getBalance();

			updateBalance(created, dto.amount());

			notifyCustomer(receiverAccount, dto, true, oldReceiverBalance);
			notifyCustomer(senderAccount, dto, true, oldSenderBalance);

			return of(created.getTransactionId());
		} catch (RuntimeException e) {
			log.error("Failed to create a transaction.", e);

			if (receiverAccount != null && oldReceiverBalance != null) {
				notifyCustomer(receiverAccount, dto, false, oldReceiverBalance);
			}

			if (senderAccount != null && oldSenderBalance != null) {
				notifyCustomer(senderAccount, dto, false, oldSenderBalance);
			}

			return Optional.empty();
		}
	}

	@Override
	public Optional<TransactionHistory> getHistory(Long customerId, TransactionCriteria criteria) {

		if (!customerRepository.existsById(customerId))
			return Optional.empty();

		var sentCriteria = new TransactionRepoCriteria(
				criteria.fromAmount(), criteria.toAmount(), criteria.message(), customerId, null);
		var receivedCriteria = new TransactionRepoCriteria(
				criteria.fromAmount(), criteria.toAmount(), criteria.message(), null, customerId);

		List<TransactionDTO> sent = mapper.toDTOs(repository.findByCriteria(sentCriteria));
		List<TransactionDTO> received = mapper.toDTOs(repository.findByCriteria(receivedCriteria));

		return of(new TransactionHistory(sent, received));
	}

	private static void updateBalance(Transaction transaction, Double amount) {
		Account receiverAccount = transaction.getReceiverAccount();
		Account senderAccount = transaction.getSenderAccount();

		receiverAccount.setBalance(receiverAccount.getBalance() + amount);
		senderAccount.setBalance(senderAccount.getBalance() - amount);
	}
	
	private void notifyCustomer(Account account, TransactionDTO dto, boolean successful, Double oldBalance) {
		var email = account.getCustomer().getEmail();
		var newBalance = account.getBalance();
		emailService.sendTransactionConfirmationEmail(email, dto, successful, oldBalance, newBalance);
	}
}
