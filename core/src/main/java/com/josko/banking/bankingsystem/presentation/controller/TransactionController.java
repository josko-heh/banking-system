package com.josko.banking.bankingsystem.presentation.controller;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Long> createTransaction(@RequestBody TransactionDTO transactionDTO) {
		var transactionId = transactionService.create(transactionDTO);

		if (transactionId.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(transactionId.get());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
