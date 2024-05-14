package com.josko.banking.bankingsystem.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

	@GetMapping(value = "{id}")
	public ResponseEntity<String> getCustomerData(@PathVariable Long id) {
		
		return ResponseEntity.ok(id.toString());
	}

}