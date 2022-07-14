package com.ws.customers.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ws.customers.bean.Account;
import com.ws.customers.bean.Transaction;
import com.ws.customers.exception.TransactionNotFoundException;
import com.ws.customers.service.TransactionService;

@RestController
public class TransactionController extends BaseController {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountController accountController;

	@PostMapping("/accounts/{accId}/transactions")
	public Transaction createTransaction(@Valid @RequestBody Transaction transaction, @PathVariable Integer accId) {
		Account account = accountController.retrieveAccount(accId);
		transaction.setAccount(account);
		transaction.setId(null);
		transaction = transactionService.saveTransaction(transaction);
		return transaction;
	}

	@GetMapping("/transactions/{id}")
	public Transaction retrieveTransaction(@PathVariable Integer id) {
		Transaction transaction = transactionService.retrieveTransaction(id);
		if (transaction == null) {
			throw new TransactionNotFoundException(
					returnTranslation("transaction.id") + id + " " + returnTranslation("not.found.message"));
		}
		return transaction;
	}

	@DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable int id) {
		Transaction transaction = transactionService.retrieveTransaction(id);
		transactionService.deleteTransaction(transaction.getId());
	}

	@GetMapping("/accounts/{accId}/transactions")
	public List<Transaction> retrieveAccountTransactions(@PathVariable Integer accId) {
		Account account = accountController.retrieveAccount(accId);
		return transactionService.retrieveAccountTransactions(account.getId());
	}
}
