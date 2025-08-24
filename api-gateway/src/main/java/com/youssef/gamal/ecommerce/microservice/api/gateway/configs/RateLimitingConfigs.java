package com.youssef.gamal.ecommerce.microservice.api.gateway.configs;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class RateLimitingConfigs {


    @Bean
    KeyResolver ipAddressKeyResolver() {
        return exchange -> {
            // Use the IP address of the client as the key for rate limiting
            String ipAddress = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            return Mono.just(ipAddress);
        };
    }
}
