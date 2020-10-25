package com.technest.controller;

import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.stereotype.Service;
import com.technest.model.Account;
import com.technest.exception.AccountNotFoundException;
import com.technest.model.Transaction;
import com.technest.repository.AccountRepository;

@Service
public class TransactionService implements ITransactionService {

	private final AccountRepository repository;

	public TransactionService(AccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public void create(Transaction transaction) throws Exception {

		Account sender = repository.findById(transaction.getFromAccountId())
				.orElseThrow(() -> new AccountNotFoundException(transaction.getFromAccountId()));
		Account recipient = repository.findById(transaction.getToAccountId())
				.orElseThrow(() -> new AccountNotFoundException(transaction.getToAccountId()));

		if (sender.getId() == recipient.getId()) {
			throw new Exception(new String("Sender and recipient accounts are the same."));
		}

		if (sameCurrency(sender.getCurrency(), recipient.getCurrency())) {

			if (sender.isTreasury()) {
				makeTransaction(sender, recipient, transaction.getAmount());
			} else {
				if (hasAmount(sender.getBalance(), transaction.getAmount())) {
					makeTransaction(sender, recipient, transaction.getAmount());
				} else {
					throw new Exception(new String("Unsufficient funds."));
				}
			}
		} else {
			throw new Exception(new String("Currency type is not supported."));
		}
	}

	public boolean hasAmount(BigDecimal accountBalance, BigDecimal amount) {
		int result = accountBalance.compareTo(amount);

		return result == 0 || result == 1;
	}

	public boolean makeTransaction(Account sender, Account recipient, BigDecimal amount) throws Exception {

		boolean completed = false;

		try {
			sender.setBalance(sender.getBalance().subtract(amount));
			repository.save(sender);
		} catch (Exception e) {
			throw new Exception(new String("Could not retract amount from sender."));
		}

		try {
			recipient.setBalance(recipient.getBalance().add(amount));
			repository.save(recipient);
		} catch (Exception e) {
			sender.setBalance(sender.getBalance().subtract(amount)); // return money to sender
			repository.save(sender);

			throw new Exception(new String("Could not add amount to recipient."));
		}

		completed = true;

		return completed;
	}

	public boolean sameCurrency(Currency senderCurrency, Currency recipientCurrency) {

		return senderCurrency.getNumericCode() == recipientCurrency.getNumericCode();
	}

}
