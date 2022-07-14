package com.ws.customers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.customers.bean.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	List<Account> findByCustomer_Id(Integer id);
}
