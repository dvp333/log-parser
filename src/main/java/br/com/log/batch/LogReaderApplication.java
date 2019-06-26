package br.com.log.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableBatchProcessing
@SpringBootApplication
public class LogReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogReaderApplication.class, args);
	}

}
