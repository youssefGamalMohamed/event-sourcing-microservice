package com.youssef.gamal.ecommerce.microservice.category.query.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.youssef.gamal.ecommerce.microservice.category")
public class MongoDBConfigs {
}
