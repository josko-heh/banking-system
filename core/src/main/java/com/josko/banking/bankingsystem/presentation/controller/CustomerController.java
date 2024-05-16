package com.josko.banking.bankingsystem.presentation.controller;

import com.josko.banking.bankingsystem.presentation.dto.CustomerDetails;
import com.josko.banking.bankingsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.notFound().build();
		}
	}

}
