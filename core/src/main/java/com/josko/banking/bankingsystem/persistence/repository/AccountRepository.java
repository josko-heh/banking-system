package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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

	@Modifying
	@Transactional
	@Query("UPDATE Account a " +
			"SET a.pastMonthTurnover = (" +
			"   SELECT SUM(t.amount) FROM Transaction t " +
			"   WHERE (t.senderAccount = a OR t.receiverAccount = a) AND t.timestamp >= :from" +
			")")
	void updatePastMonthTurnover(@Param("from") Instant from);
}
