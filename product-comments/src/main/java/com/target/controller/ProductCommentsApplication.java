package com.target.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.target.*")
public class ProductCommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCommentsApplication.class, args);
	}

}
