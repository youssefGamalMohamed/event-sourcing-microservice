package com.youssef.gamal.ecommerce.microservice.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.youssef.gamal.ecommerce.microservice.category.*")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.youssef.gamal.ecommerce.microservice.category.*")
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}
}
