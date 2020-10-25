package com.technest.controller;

import com.technest.model.Transaction;

public interface ITransactionService {
	
	void create(Transaction transaction) throws Exception;

}
