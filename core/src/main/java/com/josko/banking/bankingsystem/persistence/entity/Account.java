package com.josko.banking.bankingsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
	
	public enum AccountType {
		SAVINGS,
		CHECKING,
		CREDIT,
		DEBIT
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(length = 34, unique = true, updatable = false, nullable = false)
	private String iban;

	@Enumerated(EnumType.ORDINAL)
	private AccountType type;

	private Double balance;

	private Double pastMonthTurnover;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}

