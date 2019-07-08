package com.ef;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@EnableJpaRepositories
@SpringBootApplication
public class Parser {
	public static void main(String[] args) {
		SpringApplication.run(Parser.class, args);
	}
}
