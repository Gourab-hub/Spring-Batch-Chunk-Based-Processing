package com.batch.chunk.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.chunk.entity.Customer;

@Component
public class MyItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		customer.setEmail(customer.getEmail().toLowerCase());
		return customer;
	}

}