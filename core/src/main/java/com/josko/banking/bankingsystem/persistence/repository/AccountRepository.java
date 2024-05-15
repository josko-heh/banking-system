package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE Account a " +
			"SET a.balance = COALESCE((SELECT SUM(t.amount) " +
			"                          FROM Transaction t " +
			"                          WHERE t.receiverAccount = a), 0) " +
			"              - COALESCE((SELECT SUM(t.amount) " +
			"                          FROM Transaction t " +
			"                          WHERE t.senderAccount = a), 0)")
	void updateAccountBalances();
}
