package com.ws.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.customers.bean.Account;
import com.ws.customers.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> retrieveCustomerAccounts(Integer customerId) {
		return accountRepository.findByCustomer_Id(customerId);
	}

	public Account retrieveAccount(Integer id) {
		Optional<Account> optional = accountRepository.findById(id);
		Account account = null;
		if (optional.isPresent()) {
			account = optional.get();
		}
		return account;
	}

	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	public void deleteAccount(Integer id) {
		accountRepository.deleteById(id);
	}
}
