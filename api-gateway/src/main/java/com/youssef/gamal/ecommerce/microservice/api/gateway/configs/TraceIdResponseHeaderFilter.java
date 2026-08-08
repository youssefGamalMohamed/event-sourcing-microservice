package com.youssef.gamal.ecommerce.microservice.api.gateway.configs;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TraceIdResponseHeaderFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(TraceIdResponseHeaderFilter.class);
    private final Tracer tracer;

    public TraceIdResponseHeaderFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Received request: {} {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath());

        exchange.getResponse().beforeCommit(() -> {
            Span currentSpan = tracer.currentSpan();
            if (currentSpan != null && currentSpan.context() != null) {
                String traceId = currentSpan.context().traceId();
                if (traceId != null && !traceId.isEmpty()) {
                    exchange.getResponse().getHeaders().set("X-Trace-Id", traceId);
                }
            }
            log.info("Completed request: {} {} with status {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI().getPath(),
                    exchange.getResponse().getStatusCode());
            return Mono.empty();
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
