package com.ws.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.customers.bean.Customer;
import com.ws.customers.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> retrieveAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer retrieveCustomer(Integer id) {
		Optional<Customer> optional = customerRepository.findById(id);
		Customer customer = null;
		if (optional.isPresent()) {
			customer = optional.get();
		}
		return customer;
	}

	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public void deleteCustomer(Integer id) {
		customerRepository.deleteById(id);
	}
}
