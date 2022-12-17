package com.example.webchatter;

import
		org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebchatterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebchatterApplication.class, args);
	}

}
