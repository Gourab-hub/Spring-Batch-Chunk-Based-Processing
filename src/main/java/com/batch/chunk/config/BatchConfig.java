package com.batch.chunk.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.batch.chunk.entity.Customer;
import com.batch.chunk.repository.CustomerRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	 @Autowired
	    private JobRepository jobRepository;

	    @Autowired
	    private PlatformTransactionManager transactionManager;

	    @Autowired
	    private CustomerRepository customerRepository;
	    
	    @Autowired
	    private MyItemProcessor myItemProcessor;

	    @Bean
	    public ItemReader<Customer> reader() {
	        List<Customer> list = Arrays.asList(
	                new Customer(1L, "Glory1", "glory@gmail.com"),
	                new Customer(3L, "John2", "john@gmail.com")
	        );
	        return new ListItemReader<>(list);
	    }


	    @Bean
	    public ItemWriter<Customer> writer() {
	        return customers -> customerRepository.saveAll(customers);
	    }

	    @Bean
	    public Step step1() {
	        return new StepBuilder("step1")
	                .repository(jobRepository)
	                .transactionManager(transactionManager)
	                .<Customer, Customer>chunk(2)
	                .reader(reader())
	                .processor(myItemProcessor)
	                .writer(writer())
	                .build();
	    }

	    @Bean
	    public Job job() {
	        return new JobBuilder("customer-job")
	                .repository(jobRepository)
	                .start(step1())
	                .build();
	    }
}
