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
	

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Customer, Customer>chunk(2).reader(myItemReader)
				.processor(myItemProcessor).writer(myItemWriter).build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job1").start(step1()).build();
	}
}
