package com.josko.banking.bankingsystem.presentation.controller;

import com.josko.banking.bankingsystem.presentation.dto.CustomerDetails;
import com.josko.banking.bankingsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

	private final CustomerService customerService;
	
	@GetMapping(value = "{id}")
	public ResponseEntity<CustomerDetails> getCustomerData(@PathVariable Long id) {
		var details = customerService.getCustomerDetails(id);
		
		if (details.isPresent()) {
			return ResponseEntity.ok(details.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
