package br.com.ilegra.process;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class IlegraApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlegraApplication.class, args);
	}

}
