package com.youssef.gamal.ecommerce.microservice.product.query.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.youssef.gamal.ecommerce.microservice.product")
public class MongoDBConfigs {
}
