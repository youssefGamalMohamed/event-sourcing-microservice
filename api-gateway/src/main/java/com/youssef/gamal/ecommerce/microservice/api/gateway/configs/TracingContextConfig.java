package com.youssef.gamal.ecommerce.microservice.api.gateway.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingContextConfig {

    @PostConstruct
    public void init() {
        reactor.core.publisher.Hooks.enableAutomaticContextPropagation();
    }
}
