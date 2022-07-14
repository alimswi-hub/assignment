package com.ws.customers.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Customer customer;
	@Size(min = 2, max = 20, message = "Account name size should be between 2 and 20 chars")
	@Column(length = 20)
	private String name;
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transactions;
	@Transient
	private BigDecimal balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public BigDecimal getBalance() {
		if (transactions != null) {
			BigDecimal debitBal = transactions.stream().filter(t -> "D".equals(t.getType())).map(x -> x.getAmount())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal creditBal = transactions.stream().filter(t -> "C".equals(t.getType())).map(x -> x.getAmount())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			balance = debitBal.subtract(creditBal);
		}

		return balance;
	}

}
