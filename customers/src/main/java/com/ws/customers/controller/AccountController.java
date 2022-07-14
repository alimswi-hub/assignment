package com.ws.customers.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ws.customers.bean.Account;
import com.ws.customers.bean.Customer;
import com.ws.customers.bean.Transaction;
import com.ws.customers.exception.AccountNotFoundException;
import com.ws.customers.service.AccountService;

@RestController
public class AccountController extends BaseController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerController customerController;

	@Autowired
	private TransactionController transactionController;

	/***
	 * @author Ali Al Moussawi
	 * @param account
	 * @param custId
	 * @param initBal
	 * @return Account
	 * @apiNote The service requested in the assignment that create an account for a
	 *          specific customer with initial balance.
	 */
	@PostMapping("customers/{custId}/accounts/{initBal}")
	public ResponseEntity<Object> createAccount(@Valid @RequestBody Account account, @PathVariable Integer custId,
			@PathVariable BigDecimal initBal) {
		Customer customer = customerController.retrieveCustomer(custId);
		account.setCustomer(customer);
		account.setId(null);
		account = accountService.saveAccount(account);
		if (BigDecimal.ZERO.compareTo(initBal) != 0) {
			Transaction transaction = new Transaction();
			transaction.setAccount(account);
			transaction.setAmount(initBal.abs());
			if (BigDecimal.ZERO.compareTo(initBal) > 0) {
				transaction.setType("C");
			} else {
				transaction.setType("D");
			}
			transaction = transactionController.createTransaction(transaction, account.getId());
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/customers/{id}/accounts")
	public List<Account> retrieveCustomerAccounts(@PathVariable Integer id) {
		Customer customer = customerController.retrieveCustomer(id);
		return accountService.retrieveCustomerAccounts(customer.getId());
	}

	@GetMapping("/accounts/{id}")
	public Account retrieveAccount(@PathVariable Integer id) {
		Account account = accountService.retrieveAccount(id);
		if (account == null) {
			throw new AccountNotFoundException(
					returnTranslation("account.id") + id + " " + returnTranslation("not.found.message"));
		}
		return account;
	}

	@DeleteMapping("/accounts/{id}")
	public void deleteAccount(@PathVariable int id) {
		Account account = retrieveAccount(id);
		accountService.deleteAccount(account.getId());
	}
}
