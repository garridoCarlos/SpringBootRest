package com.technest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.technest.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api)")
public class TransactionController {
	
	private final ITransactionService transactionService;
	
	@Autowired
	TransactionController(ITransactionService service) {
		this.transactionService = service;
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<String> newTransaction(@RequestBody Transaction newTransaction) {
		try {
			transactionService.create(newTransaction);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
