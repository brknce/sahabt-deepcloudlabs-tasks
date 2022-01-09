package com.example.banking.service.business;

import org.springframework.stereotype.Service;

import com.example.banking.document.CustomerDocument;
import com.example.banking.repository.CustomerMongoRepository;
import com.example.banking.service.CustomerService;
import com.example.banking.service.TransferService;

@Service
public class StandardTransferService implements TransferService {
	private CustomerMongoRepository customerMongoRepository;
	private CustomerService customerService;

	public StandardTransferService(CustomerMongoRepository customerMongoRepository, CustomerService customerService) {
		this.customerMongoRepository = customerMongoRepository;
		this.customerService = customerService;
	}

	@Override
	public void transfer(String fromIdentity, String fromIban, String toIdentity, String toIban, double amount)
			throws Exception {
		// TODO: implement the transfer logic

		CustomerDocument cus1 = customerService.findCustomerByIdentity(fromIdentity);
		CustomerDocument cus2 = customerService.findCustomerByIdentity(toIdentity);

		cus1.getAccounts().stream().filter(acc -> acc.getIban().equals(fromIban)).findAny().orElse(null)
				.withdraw(amount);
		cus2.getAccounts().stream().filter(acc -> acc.getIban().equals(toIban)).findAny().orElse(null).deposit(amount);

		customerMongoRepository.save(cus1);
		customerMongoRepository.save(cus2);

	}

}