package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import com.josko.banking.bankingsystem.persistence.repository.criteria.TransactionRepoCriteria;
import com.josko.banking.bankingsystem.persistence.repository.custom.FindByCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<Transaction, Long>,
		FindByCriteria<TransactionRepoCriteria, Transaction>,
		JpaSpecificationExecutor<Transaction> {

}
