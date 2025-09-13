package com.youssef.gamal.ecommerce.microservice.product.common.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class TracingConfigs {

    @Bean
    public RequestInterceptor tracingInterceptor(io.micrometer.tracing.Tracer tracer) {
        return template -> {
            var span = tracer.currentSpan();
            if (span != null) {
                var context = span.context();
                template.header("traceparent", "00-" + context.traceId() + "-" + context.spanId() + "-01");
                // Optional for B3 backward compatibility:
                template.header("x-b3-traceid", context.traceId());
                template.header("x-b3-spanid", context.spanId());
            }
        };
    }
    
}
