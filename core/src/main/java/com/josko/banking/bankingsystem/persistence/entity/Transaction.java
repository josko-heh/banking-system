package com.josko.banking.bankingsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(nullable = false)
	private Double amount;

	private String message;

	@Column(nullable = false)
	private Instant timestamp;

	@OneToOne
	@JoinColumn(name = "senderAccountId", nullable = false)
	private Account senderAccount;

	@OneToOne
	@JoinColumn(name = "receiverAccountId", nullable = false)
	private Account receiverAccount;
}
