package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
