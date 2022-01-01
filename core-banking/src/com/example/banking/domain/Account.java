package com.example.banking.domain;

/**
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class Account extends Object {
	private AccountStatus status = AccountStatus.ACTIVE;
	// Notes:
	// iban is an attribute/state/property/data
	// iban is an instance and reference variable
	// iban is automatically initialized to null
	private String iban;

	// Notes:
	// balance is an instance and value-typed variable
	// balance is automatically initialized to 0.0
	double balance;

	// Notes:
	// Constructor is a special method used to initialize the object state
	// new always triggers a Constructor
	// Constructor cannot return a value!
	public Account(String iban, double balance) {
		this.iban = iban;
		this.balance = balance;
	}
	
	public Account(String iban, double balance, AccountStatus status){
		this.iban = iban;
		this.balance = balance;
		this.status = status;
	}
	
	public String getIban() {
		return this.iban;
	}

	public double getBalance() {
		return this.balance;
	}
	
	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	// Notes:
	// deposit is a business method
	// returning a boolean value to refer whether the method is successful or not
	// is generally not a good idea!
	// we will give a better solution later on the course.
	public boolean deposit(double amount) {
		// validation
		if (amount <= 0)
			return false;
		// business logic
		this.balance = this.balance + amount;
		return true;
	}

	// withdraw is a business method
	public boolean withdraw(double amount) {
		// validation
		if (amount <= 0)
			return false;
		// business rule
		if (amount > this.balance)
			return false;
		// this.balance = this.balance - amount;
		this.balance -= amount;
		return true;
	}

	// toString is a utility method
	// its aim is to produce a string representation of the object
	@Override
	public String toString() {
		return "Account [iban=" + this.iban + ", balance=" + this.balance + ", status=" + this.status + "]";
	}

	public double withdrawAll() {
		var balance = this.balance;
		this.balance = 0.0;
		return balance;
	}

}