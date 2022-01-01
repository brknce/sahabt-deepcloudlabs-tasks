package com.example.banking.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Customer {
	private final String identity;
	private String fullName;
	private double totalBalance;
	//private List<Account> accounts = new ArrayList<>();
	private Map<String,Account> accounts = new LinkedHashMap<>();

	public Customer(String identity, String fullName) {
		this.identity = identity;
		this.fullName = fullName;
	}

	public double getTotalBalance(){
		   //TODO: returns Customer's total balance in all his/her accounts. 
		return accounts.values().stream().mapToDouble(Account::getBalance).reduce(0, (num1,num2)->num1 + num2);
	}  
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdentity() {
		return identity;
	}

	public void addAccount(String iban, Account account) {
		accounts.put(iban,account);
	}

	// Overloading: Same class & method name, different signature
	// Overriding: Inherited Classes, same method name & signature
	public Optional<Account> removeAccount(int index) {
		if (index < 0 || index >= accounts.size())
			return Optional.empty();
		return Optional.of(accounts.remove(accounts.keySet().stream().collect(Collectors.toList()).get(index)));
	}

	public Optional<Account> removeAccount(String iban) {
		Account foundAccount = null;
		for (var account : accounts.values()) {
			if (account.getIban().equals(iban)) {
				foundAccount = account;
				break;
			}
		}
		if (Objects.nonNull(foundAccount)) {
			accounts.remove(iban);
		}
		return Optional.ofNullable(foundAccount);
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts.values().stream().collect(Collectors.toList())) ;
	}

	@Override
	public String toString() {
		return "Customer [identity=" + identity + ", fullName=" + fullName + ", accounts=" + accounts + "]";
	}

}
