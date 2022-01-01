package com.example.banking.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.banking.service.TransferService;

public class Bank implements TransferService{
	private final int id;
	private String commercialName;
	private final BankType type;
	private Map<String, Customer> customers = new HashMap<>();

	Bank(int id, String commercialName, BankType type) {
		this.id = id;
		this.commercialName = commercialName;
		this.type = type;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public int getId() {
		return id;
	}

	public BankType getType() {
		return type;
	}

	public List<Customer> getCustomers() {
		if (this.customers.size() > 0) {
			return Collections.unmodifiableList(customers.values().stream().collect(Collectors.toList()));
		}
		return Collections.emptyList();
	}

	public Customer createCustomer(String identity, String fullname) {
		Customer c1 = new Customer(identity, fullname);
		customers.put(identity, c1);
		return c1;
	}

	public Optional<Customer> findCustomerByIdentity(String identity) {
		Optional<Customer> c1 = Optional.ofNullable(customers.get(identity));
		if (c1.isPresent()) {
			return c1;
		}
		return Optional.empty();
	}

	
	public double getTotalBalance(AccountStatus... status) {
		if(status.length >0) {
			return customers.values().stream().map(Customer::getAccounts).flatMap(Collection::stream)
					.filter(acc -> Arrays.stream(status).anyMatch(acc.getStatus()::equals))
					.mapToDouble(Account::getBalance)
					.reduce(0, (num1, num2) -> num1 + num2);
		}
		 return 0.0;
		
	}

	@Override
	public boolean transfer(String fromIdentity, String fromIban, String toIdentity, String toIban, double amount) {
		Account fromA = null;
		Account fromB = null;
		
		for(var acc : customers.get(fromIdentity).getAccounts()) {
			if(acc.getIban() == fromIban) {
				fromA = acc;
				break;
			}
		}
	
		for(var acc : customers.get(toIdentity).getAccounts()) {
			if(acc.getIban() == toIban) {
				fromB = acc;
				break;
			}
		}
		
		if(fromA.withdraw(amount)) {
			fromB.deposit(amount);
			return true;
		}
		
		return false;
	}

}
