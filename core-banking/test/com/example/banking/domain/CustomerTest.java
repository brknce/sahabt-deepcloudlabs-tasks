package com.example.banking.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
class CustomerTest {

	@ParameterizedTest
	@DisplayName("create customers successfuly")
	@CsvFileSource(resources = "customers.csv")
	void createCustomersSuccessfuly(String identity, String fullname) {
		// 1. Test Fixture/Setup
		// 2. Call exercise method
		var customer = new Customer(identity, fullname);
		// 3. Verification
		assertEquals(identity, customer.getIdentity());
		assertEquals(fullname, customer.getFullName());
		// 4. Tear-down
	}

	@Test
	@DisplayName("setting customer's full name successfuly")
	void setCustomerFullNameSuccessfuly() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		// 2. Call exercise method
		customer.setFullName("jack shephard");
		// 3. Verification
		assertEquals("jack shephard", customer.getFullName());
		// 4. Tear-down
	}

	@Test
	@DisplayName("add account to a customer  successfuly")
	void addAccountsToCustomerSuccessfuly() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		// 2. Call exercise method
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 3. Verification
		assertEquals(3, customer.getAccounts().size());
		assertTrue(customer.getAccounts().contains(acc1));
		assertTrue(customer.getAccounts().contains(acc2));
		assertTrue(customer.getAccounts().contains(acc3));
		// 4. Tear-down
	}

	@Test
	@DisplayName("remove account by iban from a customer successfuly")
	void removeAccountByIbanFromCustomerSuccessfuly() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 2. Call exercise method
		// 3. Verification
		assertTrue(customer.removeAccount("TR480006276391165871184343").isPresent());
		assertEquals(2, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc1));
		assertTrue(customer.removeAccount("TR520006284786839673978754").isPresent());
		assertEquals(1, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc2));
		assertTrue(customer.removeAccount("TR840006266285713911717977").isPresent());
		assertEquals(0, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc3));

		// 4. Tear-down
	}

	@Test
	@DisplayName("remove account by iban from a customer fails")
	void removeAccountByIbanFromCustomerFails() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 2. Call exercise method
		// 3. Verification
		assertEquals(3, customer.getAccounts().size());
		assertTrue(customer.removeAccount("TR330006271693398443527616").isEmpty());
		// 4. Tear-down
	}

	@Test
	@DisplayName("remove account by index from a customer successfuly")
	void removeAccountByIndexFromCustomerSuccessfuly() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 2. Call exercise method
		// 3. Verification
		System.out.println(customer.getAccounts());
		assertTrue(customer.removeAccount(2).isPresent());
		System.out.println(customer.getAccounts());
		assertEquals(2, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc3));
		assertTrue(customer.removeAccount(1).isPresent());
		assertEquals(1, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc2));
		assertTrue(customer.removeAccount(0).isPresent());
		assertEquals(0, customer.getAccounts().size());
		assertFalse(customer.getAccounts().contains(acc1));

		// 4. Tear-down
	}

	@Test
	@DisplayName("remove account by index from a customer fails")
	void removeAccountByIndexFromCustomerFails() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 2. Call exercise method
		// 3. Verification
		assertEquals(3, customer.getAccounts().size());
		assertTrue(customer.removeAccount(-1).isEmpty());
		assertTrue(customer.removeAccount(3).isEmpty());
		// 4. Tear-down
	}

	@Test
	void getTotalBalanceTest() {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		var acc1 = new Account("TR480006276391165871184343", 10_000);
		var acc2 = new CheckingAccount("TR520006284786839673978754", 20_000, 5_000);
		var acc3 = new Account("TR840006266285713911717977", 30_000);
		customer.addAccount(acc1.getIban(), acc1);
		customer.addAccount(acc2.getIban(), acc2);
		customer.addAccount(acc3.getIban(), acc3);
		// 2. Call exercise method
		// 3. Verification
		double sum = acc1.getBalance() + acc2.getBalance() + acc3.getBalance();
		assertEquals(sum, customer.getTotalBalance());

	}

	@Test
	void toStringShouldStartWithCustomer() throws Exception {
		// 1. Test Fixture/Setup
		var customer = new Customer("94954016200", "Jack Bauer");
		// 2. Call exercise method
		// 3. verification
		assertTrue(customer.toString().startsWith("Customer"));
	}
}
