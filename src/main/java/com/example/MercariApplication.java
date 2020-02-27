package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MercariApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercariApplication.class, args);
	}

}
