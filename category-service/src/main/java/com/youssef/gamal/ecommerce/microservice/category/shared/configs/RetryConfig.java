package com.youssef.gamal.ecommerce.microservice.category.shared.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry  // ✅ Essential for @Retryable to work!
public class RetryConfig {
}