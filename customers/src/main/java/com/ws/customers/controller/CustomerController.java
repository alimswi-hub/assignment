package com.ws.customers.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ws.customers.bean.Customer;
import com.ws.customers.exception.CustomerNotFoundException;
import com.ws.customers.service.CustomerService;

@RestController
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AccountController accountController;

	@GetMapping("/customers")
	public List<Customer> retrieveAllCustomers() {
		return customerService.retrieveAllCustomers();
	}

	/***
	 * @author Ali Al Moussawi
	 * @param id
	 * @return Customer
	 * @apiNote The service requested in the assignment that retrieve customer
	 *          details, accounts and transactions.
	 */
	@GetMapping("/customers/{id}")
	public Customer retrieveCustomer(@PathVariable int id) {
		Customer customer = customerService.retrieveCustomer(id);
		if (customer == null) {
			throw new CustomerNotFoundException(
					returnTranslation("customer.id") + id + " " + returnTranslation("not.found.message"));
		}
		return customer;
	}

	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		customer.setId(null);
		return customerService.saveCustomer(customer);
	}

	@PutMapping("/customers/{id}")
	public Customer updateCustomer(@Valid @RequestBody Customer customer, @PathVariable int id) {
		Customer var = customerService.retrieveCustomer(id);
		if (var != null) {
			customer.setId(id);
		} else {
			customer.setId(null);
		}
		return customerService.saveCustomer(customer);
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable int id) {
		Customer customer = retrieveCustomer(id);
		customerService.deleteCustomer(customer.getId());
	}
}
