# API Gateway Swagger Aggregation & Distributed Tracing Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Centralize OpenAPI/Swagger UI testing at API Gateway (`http://localhost:9999/swagger-ui.html`), route Category Service endpoints through the gateway, and enable distributed trace/span ID logging and Zipkin reporting.

**Architecture:** Add SpringDoc WebFlux UI and Micrometer Brave Tracing to `api-gateway`. Configure Spring Cloud Gateway to route downstream `/v3/api-docs` endpoints and application endpoints for both `category-service` and `product-service`. Configure Swagger UI multi-URL aggregation and Zipkin tracing parameters in `api-gateway`'s `application.yml`.

**Tech Stack:** Java 17, Spring Boot 3.4.5, Spring Cloud Gateway (WebFlux), SpringDoc OpenAPI v2.8.5, Micrometer Tracing (Brave + Zipkin).

## Global Constraints

- Spring Boot version: 3.4.5
- Spring Cloud version: 2024.0.1
- SpringDoc OpenAPI version: 2.8.5
- API Gateway Port: 9999
- Category Service Port: 7070
- Product Service Port: 8088

---

### Task 1: Add WebFlux OpenAPI and Micrometer Tracing Dependencies to `api-gateway`

**Files:**
- Modify: `api-gateway/pom.xml:33-66`

**Interfaces:**
- Consumes: Maven dependencies for Spring Cloud Gateway.
- Produces: WebFlux OpenAPI UI and Micrometer Brave tracing classpath support for `api-gateway`.

- [ ] **Step 1: Edit `api-gateway/pom.xml` to add SpringDoc WebFlux and Micrometer Tracing dependencies**

Add the following dependency blocks inside `<dependencies>` in `api-gateway/pom.xml`:
```xml
		<!-- SpringDoc OpenAPI UI for Spring WebFlux / Gateway -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
			<version>2.8.5</version>
		</dependency>

		<!-- Zipkin and Micrometer Tracing for Gateway -->
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-tracing-bridge-brave</artifactId>
		</dependency>
		<dependency>
			<groupId>io.zipkin.reporter2</groupId>
			<artifactId>zipkin-reporter-brave</artifactId>
		</dependency>
```

- [ ] **Step 2: Verify `api-gateway` Maven compilation**

Run: `mvn clean compile -f api-gateway/pom.xml`  
Expected: `BUILD SUCCESS`

- [ ] **Step 3: Commit Task 1 changes**

```bash
git add api-gateway/pom.xml
git commit -m "feat(gateway): add springdoc webflux and micrometer tracing dependencies"
```

---

### Task 2: Configure OpenAPI & Swagger Settings in Downstream Services

**Files:**
- Modify: `product-service/src/main/resources/application.yml:84-95`
- Verify: `category-service/src/main/resources/application.yml:86-91`

**Interfaces:**
- Consumes: Downstream Spring WebMVC application configs.
- Produces: Explicit `/v3/api-docs` and `/swagger-ui.html` paths for `product-service` and `category-service`.

- [ ] **Step 1: Add SpringDoc configuration to `product-service/src/main/resources/application.yml`**

Add the following block to `product-service/src/main/resources/application.yml`:
```yaml
# Swagger UI & OpenAPI docs
springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

- [ ] **Step 2: Verify `category-service` OpenAPI config**

Check `category-service/src/main/resources/application.yml` has:
```yaml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

- [ ] **Step 3: Verify downstream services build**

Run: `mvn clean compile -f category-service/pom.xml && mvn clean compile -f product-service/pom.xml`  
Expected: `BUILD SUCCESS` for both services.

- [ ] **Step 4: Commit Task 2 changes**

```bash
git add product-service/src/main/resources/application.yml
git commit -m "config(product-service): add explicit springdoc openapi path configuration"
```

---

### Task 3: Configure Category Service Routes, OpenAPI Spec Proxying, Swagger UI Aggregation, and Tracing in `api-gateway`

**Files:**
- Modify: `api-gateway/src/main/resources/application.yml`

**Interfaces:**
- Consumes: Downstream API specifications and ports (7070 and 8088).
- Produces: Aggregated Swagger UI at `/swagger-ui.html`, `/category-service/v3/api-docs` proxy, `/product-service/v3/api-docs` proxy, category application routes, and trace ID logging.

- [ ] **Step 1: Update `api-gateway/src/main/resources/application.yml` with routes, swagger aggregation, and management tracing**

Update `api-gateway/src/main/resources/application.yml` to include:

```yaml
# Spring Boot Application Configuration
spring:
  application:
    name: api-gateway

  cloud:
    gateway:
      httpserver:
        wiretap: true
      httpclient:
        wiretap: true

      routes:
        # --- OpenAPI Docs Route for Category Service ---
        - id: category_service_v3_api_docs
          uri: http://localhost:7070
          predicates:
            - Path=/category-service/v3/api-docs
          filters:
            - RewritePath=/category-service/v3/api-docs, /ecommerce/api/v1/v3/api-docs

        # --- OpenAPI Docs Route for Product Service ---
        - id: product_service_v3_api_docs
          uri: http://localhost:8088
          predicates:
            - Path=/product-service/v3/api-docs
          filters:
            - RewritePath=/product-service/v3/api-docs, /ecommerce/api/v1/v3/api-docs

        # --- Category Command Service (POST Create Category) ---
        - id: category_command_service_post_create
          uri: http://localhost:7070
          predicates:
            - Path=/ecommerce/api/v1/categories-command
            - Method=POST
          filters:
            - RewritePath=/ecommerce/api/v1/categories-command, /ecommerce/api/v1/categories
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 5
                redis-rate-limiter.burstCapacity: 10
                redis-rate-limiter.requestedTokens: 1

        # --- Category Command Service (PUT Update Category) ---
        - id: category_command_service_put_update
          uri: http://localhost:7070
          predicates:
            - Path=/ecommerce/api/v1/categories-command/{id}
            - Method=PUT
          filters:
            - RewritePath=/ecommerce/api/v1/categories-command/(?<segment>.*), /ecommerce/api/v1/categories/${segment}
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 5
                redis-rate-limiter.burstCapacity: 10
                redis-rate-limiter.requestedTokens: 1

        # --- Category Query Service (GET Category History) ---
        - id: category_query_service_get_history
          uri: http://localhost:7070
          predicates:
            - Path=/ecommerce/api/v1/categories-query/{id}/history
            - Method=GET
          filters:
            - RewritePath=/ecommerce/api/v1/categories-query/(?<segment>.*), /ecommerce/api/v1/categories/${segment}
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 50
                redis-rate-limiter.burstCapacity: 100
                redis-rate-limiter.requestedTokens: 1

        # --- Category Query Service (GET Single Category) ---
        - id: category_query_service_get_one
          uri: http://localhost:7070
          predicates:
            - Path=/ecommerce/api/v1/categories-query/{id}
            - Method=GET
          filters:
            - RewritePath=/ecommerce/api/v1/categories-query/(?<segment>.*), /ecommerce/api/v1/categories/${segment}
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
                redis-rate-limiter.requestedTokens: 1

        # --- Product Query Service - GET History ---
        - id: product_query_service_get_history
          uri: http://localhost:8088
          predicates:
            - Path=/ecommerce/api/v1/products-query/{id}/history
            - Method=GET
          filters:
            - RewritePath=/ecommerce/api/v1/products-query/(?<segment>.*), /ecommerce/api/v1/products/${segment}
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 50
                redis-rate-limiter.burstCapacity: 100
                redis-rate-limiter.requestedTokens: 1

        # --- Product Query Service - GET Single Item ---
        - id: product_query_service_get_one
          uri: http://localhost:8088
          predicates:
            - Path=/ecommerce/api/v1/products-query/{id}
            - Method=GET
          filters:
            - RewritePath=/ecommerce/api/v1/products-query/(?<segment>.*), /ecommerce/api/v1/products/${segment}
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
                redis-rate-limiter.requestedTokens: 1

        # --- Product Command Service (POST Create Product) ---
        - id: product_command_service_post_create
          uri: http://localhost:8088
          predicates:
            - Path=/ecommerce/api/v1/products-command
            - Method=POST
          filters:
            - RewritePath=/ecommerce/api/v1/products-command, /ecommerce/api/v1/products
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 5
                redis-rate-limiter.burstCapacity: 10
                redis-rate-limiter.requestedTokens: 1

        # --- Product Command Service (PUT Update Product) ---
        - id: product_command_service_put_update
          uri: http://localhost:8088
          predicates:
            - Path=/ecommerce/api/v1/products-command/{id}
            - Method=PUT
          filters:
            - RewritePath=/ecommerce/api/v1/products-command/(?<segment>.*), /ecommerce/api/v1/products/${segment}
            - name: RequestRateLimiter
              args:
                key-resolver: "#{@ipAddressKeyResolver}"
                redis-rate-limiter.replenishRate: 5
                redis-rate-limiter.burstCapacity: 10
                redis-rate-limiter.requestedTokens: 1

  data:
    redis:
      host: localhost
      port: 6380

# SpringDoc OpenAPI Aggregation Configuration for Gateway
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    urls:
      - name: Category Service API
        url: /category-service/v3/api-docs
      - name: Product Service API
        url: /product-service/v3/api-docs

# Distributed Tracing & Zipkin Configuration
management:
  tracing:
    enabled: true
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans

# Server Configuration
server:
  port: 9999

# Logging Configuration
logging:
  pattern:
    level: "%5p [${spring.application.name:},%X{traceId:-},%X{spanId:-}]"
  level:
    root: INFO
    org:
      springframework:
        cloud:
          gateway: INFO
```

- [ ] **Step 2: Verify `api-gateway` compilation**

Run: `mvn clean compile -f api-gateway/pom.xml`  
Expected: `BUILD SUCCESS`

- [ ] **Step 3: Commit Task 3 changes**

```bash
git add api-gateway/src/main/resources/application.yml
git commit -m "config(gateway): add category routes, swagger ui aggregation, and micrometer tracing configs"
```

---

### Task 4: End-to-End Build & Functional Verification

**Files:**
- Execute build: `mvn clean package -DskipTests`

- [ ] **Step 1: Build all three microservices**

Run: `mvn clean package -DskipTests`  
Expected: `BUILD SUCCESS` for `api-gateway`, `category-service`, and `product-service`.

- [ ] **Step 2: Commit final configuration state**

```bash
git add .
git commit -m "chore: complete swagger aggregation and distributed tracing setup across services"
```
