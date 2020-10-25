package com.technest.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.technest.exception.AccountNotFoundException;
import com.technest.model.Account;
import com.technest.repository.AccountRepository;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	private final AccountRepository repository;
	
	AccountController (AccountRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/accounts")
	List<Account> all() {
		return repository.findAll();
	}
	
	@PostMapping("/accounts")
	Account newAccount(@RequestBody Account newAccount) {
		return repository.save(newAccount);
	}
	
	@GetMapping("/accounts/{id}")
	Account getAccount(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
	}
	
	@PutMapping("/accounts/{id}")
	Account updateAccount(@RequestBody Account updatedAccount, @PathVariable Long id) {

		return repository.findById(id).map(account -> {
			account.setName(updatedAccount.getName());
			account.setCurrency(updatedAccount.getCurrency());
			account.setBalance(updatedAccount.getBalance());
			return repository.save(account);
		}).orElseGet(() -> {
			updatedAccount.setId(id);
			return repository.save(updatedAccount);
		});
	}
	
	@DeleteMapping("/accounts/{id}")
	void deleteAccount(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
