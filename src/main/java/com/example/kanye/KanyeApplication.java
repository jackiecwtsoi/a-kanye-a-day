package com.example.kanye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanyeApplication {
	private static final Logger logger = LoggerFactory.getLogger(KanyeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KanyeApplication.class, args);
	}

}
