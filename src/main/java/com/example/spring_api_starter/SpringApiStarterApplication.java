package com.example.spring_api_starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.spring_api_starter.repositories")
@EntityScan(basePackages = "com.example.spring_api_starter.entities")
public class SpringApiStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApiStarterApplication.class, args);
	}

}
