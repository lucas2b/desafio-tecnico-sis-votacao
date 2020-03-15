package com.lucas.sisvotacao.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SisVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisVotacaoApplication.class, args);
	}

}
