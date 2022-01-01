package com.example.banking.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;

class BankTest {

	@Test
	void test() {
		//Test for Constructor...
		Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		
		
		//test for getter methods...
		assertEquals(1, bank.getId());
		assertEquals("GarantiBbva", bank.getCommercialName());
		assertEquals(BankType.PUBLÝC, bank.getType());
		
		//test for setter methods...
		bank.setCommercialName("Yapi Kredi");
		assertEquals("Yapi Kredi", bank.getCommercialName());
		
		//test for createCustomer method...
		var customer = bank.createCustomer("94954016200", "Jack Bauer");
		var customer2 = new Customer("94954016200", "Jack Bauer");
		assertEquals(customer.getIdentity(), customer2.getIdentity());
		
		//test for getCustomers method...
		var cList = bank.getCustomers();
		assertEquals(1, cList.size());
		
		//test for findCustomerByIdentity method..
		var c1 = bank.findCustomerByIdentity("94954016200");
		assertTrue(c1.isPresent());
	}
	
	@Test
	void getCustomersForEmpytList() {
		Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		assertEquals(Collections.emptyList(), bank.getCustomers());
	}
	
	@Test
	void findCustomerByIdentityForEmptyCustomerList() {
		Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		var c1 = bank.findCustomerByIdentity("94954016200");
		assertFalse(c1.isPresent());
	}
	
	@Test
	void getTotalBalanceWithAccountStatus() {
		Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		
		var c1 = bank.createCustomer("94954016200", "Jack Bauer");
		var c2 = bank.createCustomer("92956016240", "Jack Jones");
		
		var acc1 = new Account("tr1", 10_000, AccountStatus.ACTIVE);
		var acc2 = new Account("tr2", 10_000, AccountStatus.BLOCKED);
		
		c1.addAccount("tr1", acc1);
		c2.addAccount("tr2", acc2);
		
		AccountStatus accStatus[] = {AccountStatus.ACTIVE};
		var result = bank.getTotalBalance(accStatus);
		
		assertEquals(10_000, result);
	}
	
	@Test
	void getTotalBalanceWithoutAccountStatus() {
Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		
		var c1 = bank.createCustomer("94954016200", "Jack Bauer");
		var c2 = bank.createCustomer("92956016240", "Jack Jones");
		
		var acc1 = new Account("tr1", 10_000, AccountStatus.ACTIVE);
		var acc2 = new Account("tr2", 10_000, AccountStatus.BLOCKED);
		
		c1.addAccount("tr1", acc1);
		c2.addAccount("tr2", acc2);
		
		var result = bank.getTotalBalance();
		assertEquals(0.0, result);
	}
	
	@Test
	void transferTest() {
		Bank bank = new Bank(1, "GarantiBbva", BankType.PUBLÝC);
		
		var c1 = bank.createCustomer("94954016200", "Jack Bauer");
		var c2 = bank.createCustomer("92956016240", "Jack Jones");
		
		var acc1 = new Account("tr1", 10_000, AccountStatus.ACTIVE);
		var acc2 = new Account("tr2", 10_000, AccountStatus.BLOCKED);
		
		c1.addAccount("tr1", acc1);
		c2.addAccount("tr2", acc2);
		
		boolean result = bank.transfer("94954016200", "tr1", "92956016240", "tr2", 5_000);
		assertTrue(result);
		assertEquals(5_000,acc1.getBalance());
		
		result = bank.transfer("94954016200", "tr1", "92956016240", "tr2", 15_000);
		assertFalse(result);
		
		result = bank.transfer("94954016200", "tr1", "92956016240", "tr2", 5_000);
		assertEquals(0.0,acc1.getBalance());
	}

}
