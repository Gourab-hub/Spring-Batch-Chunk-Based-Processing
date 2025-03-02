package com.batch.chunk.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.chunk.entity.Customer;

@Component
public class MyItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
	    if (customer.getName().matches(".*\\d.*")) { 
	        throw new InvalidCustomerException("Invalid Customer Name: " + customer.getName());
	    }
	    customer.setEmail(customer.getEmail().toLowerCase());
	    return customer;
	}
}
