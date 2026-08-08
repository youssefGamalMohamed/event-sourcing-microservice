# Design Specification: API Gateway Swagger Aggregation & Distributed Tracing

**Date**: 2026-08-08  
**Target Services**: `api-gateway`, `category-service`, `product-service`  

---

## 1. Executive Summary

This design specification details the technical architecture for:
1. Centralizing OpenAPI/Swagger UI testing at the API Gateway (`http://localhost:9999/swagger-ui.html`) for all microservices (`category-service` and `product-service`).
2. Adding complete API Gateway routes for `category-service` (commands and queries) alongside existing `product-service` routes.
3. Enabling distributed tracing (Micrometer Tracing + Brave + Zipkin) and Trace ID/Span ID logging across `api-gateway` on startup and request flows.

---

## 2. Architecture & Service Overview

```
                      ┌──────────────────────────────────────────────┐
                      │    API Gateway (Port 9999)                   │
                      │  - Spring Cloud Gateway (WebFlux)            │
                      │  - Central Swagger UI (SpringDoc WebFlux)    │
                      │  - Micrometer Tracing (Brave + Zipkin)       │
                      └──────┬─────────────────────────────┬─────────┘
                             │                             │
        /category-service/v3/api-docs             /product-service/v3/api-docs
        /ecommerce/api/v1/categories-*           /ecommerce/api/v1/products-*
                             │                             │
                             ▼                             ▼
              ┌──────────────────────────┐    ┌──────────────────────────┐
              │ Category Service (7070)  │    │  Product Service (8088)  │
              │ - WebMVC SpringDoc       │    │  - WebMVC SpringDoc      │
              │ - Postgres & MongoDB     │    │  - Postgres & MongoDB    │
              └──────────────────────────┘    └──────────────────────────┘
```

---

## 3. Component Details & Modifications

### 3.1 `api-gateway` Dependencies (`api-gateway/pom.xml`)
- Add `org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.5` for Spring WebFlux OpenAPI UI aggregation.
- Add `io.micrometer:micrometer-tracing-bridge-brave` for Micrometer/Brave tracing support.
- Add `io.zipkin.reporter2:zipkin-reporter-brave` for Zipkin span reporting.

### 3.2 Downstream Services OpenAPI Configuration
- **`product-service` (`product-service/src/main/resources/application.yml`)**:
  - Add explicit SpringDoc OpenAPI configuration:
    ```yaml
    springdoc:
      swagger-ui:
        path: /swagger-ui.html
      api-docs:
        path: /v3/api-docs
    ```
- **`category-service` (`category-service/src/main/resources/application.yml`)**:
  - Retain existing `springdoc.swagger-ui.path: /swagger-ui.html` and `springdoc.api-docs.path: /v3/api-docs`.

### 3.3 API Gateway Routing & Swagger UI Aggregation (`api-gateway/src/main/resources/application.yml`)

#### 3.3.1 Downstream OpenAPI Specs Routing
- Gateway Route `category_service_v3_api_docs`:
  - Path: `/category-service/v3/api-docs`
  - RewritePath: `/category-service/v3/api-docs` -> `/ecommerce/api/v1/v3/api-docs`
  - Target URI: `http://localhost:7070`
- Gateway Route `product_service_v3_api_docs`:
  - Path: `/product-service/v3/api-docs`
  - RewritePath: `/product-service/v3/api-docs` -> `/ecommerce/api/v1/v3/api-docs`
  - Target URI: `http://localhost:8088`

#### 3.3.2 Category Service Application Gateway Routes
- `category_command_service_post_create`: `POST /ecommerce/api/v1/categories-command` -> `http://localhost:7070/ecommerce/api/v1/categories`
- `category_command_service_put_update`: `PUT /ecommerce/api/v1/categories-command/{id}` -> `http://localhost:7070/ecommerce/api/v1/categories/{id}`
- `category_query_service_get_history`: `GET /ecommerce/api/v1/categories-query/{id}/history` -> `http://localhost:7070/ecommerce/api/v1/categories/{id}/history`
- `category_query_service_get_one`: `GET /ecommerce/api/v1/categories-query/{id}` -> `http://localhost:7070/ecommerce/api/v1/categories/{id}`

#### 3.3.3 Centralized Swagger UI Configuration
```yaml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    urls:
      - name: Category Service API
        url: /category-service/v3/api-docs
      - name: Product Service API
        url: /product-service/v3/api-docs
```

### 3.4 Distributed Tracing & Trace ID Logging Configuration
- Add Actuator & Tracing configuration to `api-gateway/src/main/resources/application.yml`:
  ```yaml
  management:
    tracing:
      enabled: true
      sampling:
        probability: 1.0
    zipkin:
      tracing:
        endpoint: http://localhost:9411/api/v2/spans

  logging:
    pattern:
      level: "%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]"
    level:
      root: INFO
      org.springframework.cloud.gateway: INFO
  ```

---

## 4. Verification Plan

1. **Compilation Check**:
   - Run `mvn clean compile` across `api-gateway`, `category-service`, and `product-service` to ensure clean build with new dependencies.
2. **OpenAPI Specification & Gateway Aggregation**:
   - Verify `http://localhost:9999/swagger-ui.html` loads the interactive Swagger UI.
   - Verify the top-right drop-down allows switching between `Category Service API` and `Product Service API`.
   - Execute sample REST calls through the Swagger UI on port 9999 and verify they route to ports 7070 (`category-service`) and 8088 (`product-service`).
3. **Distributed Tracing Verification**:
   - Start `api-gateway` and verify that log lines output trace headers in format `INFO [api-gateway,traceId,spanId]`.
   - Issue HTTP requests to port 9999 and verify that incoming trace IDs propagate downstream and spans are sent to Zipkin (`http://localhost:9411`).
