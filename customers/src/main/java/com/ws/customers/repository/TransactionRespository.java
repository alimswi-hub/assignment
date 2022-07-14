package com.ws.customers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.customers.bean.Transaction;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction, Integer> {
	List<Transaction> findByAccount_Id(Integer id);
}
