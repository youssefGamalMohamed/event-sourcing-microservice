package com.youssef.gamal.ecommerce.microservice.product;

import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.youssef.gamal.ecommerce.microservice.product.*")
@EnableFeignClients(basePackages = "com.youssef.gamal.ecommerce.microservice.product.*")
@EnableDiscoveryClient
@EnableAutoConfiguration
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

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
