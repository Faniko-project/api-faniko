package com.faniko.api_faniko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.faniko.api_faniko.repositories")
public class ApiFanikoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFanikoApplication.class, args);
	}

}
