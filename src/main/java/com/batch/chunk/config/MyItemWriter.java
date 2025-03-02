package com.batch.chunk.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.chunk.entity.Customer;
import com.batch.chunk.repository.CustomerRepository;

@Component
public class MyItemWriter implements ItemWriter<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        System.out.println("Writing Customers: " + items);
        customerRepository.saveAll(items);
    }
}