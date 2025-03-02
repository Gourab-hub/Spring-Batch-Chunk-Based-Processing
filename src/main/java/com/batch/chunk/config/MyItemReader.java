package com.batch.chunk.config;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.chunk.entity.Customer;
import com.batch.chunk.repository.CustomerRepository;

@Component
public class MyItemReader implements ItemReader<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    private Iterator<Customer> customerIterator;

    @Override
    public Customer read() throws Exception {
        if (customerIterator == null) {
            List<Customer> customers = customerRepository.findAll();
            customerIterator = customers.iterator();
        }
        if (customerIterator.hasNext()) {
            return customerIterator.next();
        }
        return null; 
    }
}
