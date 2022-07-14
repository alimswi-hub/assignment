package com.ws.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.customers.bean.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
