package com.josko.banking.bankingsystem.presentation.controller;

import com.josko.banking.bankingsystem.presentation.dto.TransactionDTO;
import com.josko.banking.bankingsystem.presentation.dto.TransactionHistory;
import com.josko.banking.bankingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Long> createTransaction(@RequestBody TransactionDTO transactionDTO) {
		var transactionId = transactionService.create(transactionDTO);

		if (transactionId.isPresent()) {
			return ResponseEntity.status(CREATED).body(transactionId.get());
		} else {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/history/{customerId}")
	public ResponseEntity<TransactionHistory> getHistory(@PathVariable Long customerId) {
		var historyOpt = transactionService.getHistory(customerId);
		
		if (historyOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		var history = historyOpt.get();
		
		if (history.received().isEmpty() && history.sent().isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(history);
		}
	}

}
