package com.josko.banking.bankingsystem.persistence.repository;

import com.josko.banking.bankingsystem.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
