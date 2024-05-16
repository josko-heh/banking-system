package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Customer;
import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.receiverAccount.customer = :customer")
	List<Transaction> findByReceiver(Customer customer);

	@Query("SELECT t FROM Transaction t WHERE t.senderAccount.customer = :customer")
	List<Transaction> findBySender(Customer customer);
}
