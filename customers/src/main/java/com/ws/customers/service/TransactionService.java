package com.ws.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.customers.bean.Transaction;
import com.ws.customers.repository.TransactionRespository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRespository transactionRespository;

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRespository.save(transaction);
	}

	public Transaction retrieveTransaction(Integer id) {
		Optional<Transaction> optional = transactionRespository.findById(id);
		Transaction transaction = null;
		if (optional.isPresent()) {
			transaction = optional.get();
		}
		return transaction;
	}

	public List<Transaction> retrieveAccountTransactions(Integer accId) {
		return transactionRespository.findByAccount_Id(accId);
	}

	public void deleteTransaction(Integer id) {
		transactionRespository.deleteById(id);
	}

}
