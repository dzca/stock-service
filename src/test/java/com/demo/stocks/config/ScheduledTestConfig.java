package com.demo.stocks.config;

import com.demo.stocks.services.FileProcessor;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile("test")
@Configuration
@EnableScheduling
@ComponentScan("com.demo.stocks.schedule")
public class ScheduledTestConfig {
    @Bean
    @Primary
    public FileProcessor fileProcessor() {
        return Mockito.mock(FileProcessor.class);
    }
}
