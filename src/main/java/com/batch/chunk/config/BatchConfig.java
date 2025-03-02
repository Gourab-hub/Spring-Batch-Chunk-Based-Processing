package com.batch.chunk.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.batch.chunk.entity.Customer;
import com.batch.chunk.repository.CustomerRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private MyItemProcessor myItemProcessor;

	@Autowired
	private MyItemReader myItemReader;
	
	@Autowired
	private MyItemWriter myItemWriter;
	
	@Autowired
	private MySkipListener mySkipListener;

	@Bean
	public Step step1(MySkipListener mySkipListener) {
	    return stepBuilderFactory.get("step1")
	            .<Customer, Customer>chunk(2)
	            .reader(myItemReader)
	            .processor(myItemProcessor)
	            .writer(myItemWriter)
	            .faultTolerant()
	            .skip(InvalidCustomerException.class) 
	            .skipLimit(3) 
	            .listener(mySkipListener)
	            .taskExecutor(taskExecutor())
	            .throttleLimit(5)
	            .build();
	}


	@Bean
	public Job job(MySkipListener mySkipListener) { // Add Listener here
	    return jobBuilderFactory.get("job1")
	            .start(step1(mySkipListener)) // Pass Listener
	            .build();
	}
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(5);  
	    executor.setMaxPoolSize(10);  
	    executor.setQueueCapacity(50); 
	    executor.setThreadNamePrefix("batch-thread-");
	    executor.initialize();
	    return executor;
	}
}
