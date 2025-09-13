package com.youssef.gamal.ecommerce.microservice.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.youssef.gamal.ecommerce.microservice.product.*")
@EnableFeignClients(basePackages = "com.youssef.gamal.ecommerce.microservice.product.*")
@EnableDiscoveryClient
@EnableAutoConfiguration
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
