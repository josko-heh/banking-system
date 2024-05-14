package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
