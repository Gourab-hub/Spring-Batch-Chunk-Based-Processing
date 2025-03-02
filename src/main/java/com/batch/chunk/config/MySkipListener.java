package com.batch.chunk.config;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.batch.chunk.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MySkipListener implements SkipListener<Customer, Customer> {
    private static final Logger logger = LoggerFactory.getLogger(MySkipListener.class);

    @Override
    public void onSkipInProcess(Customer item, Throwable t) {
        logger.warn("Skipping Customer: {} due to {}", item.getName(), t.getMessage());
    }

    @Override
    public void onSkipInWrite(Customer item, Throwable t) {
        System.out.println("Skipping Customer: " + item.getName() + " during Write due to: " + t.getMessage());
    }

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("Skipping during Read due to: " + t.getMessage());
    }
}
