# Graph Report - event-sourcing-microservice  (2026-08-09)

## Corpus Check
- 71 files · ~27,699 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 552 nodes · 1031 edges · 38 communities (27 shown, 11 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 54 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `85d0de82`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Product
- Builder
- ProductEvent
- org.springframework.context.annotation.Configuration
- Builder
- ProductMapper
- Category
- CategoryEvent
- lombok.extern.slf4j.Slf4j
- Product Service Configuration
- org.springframework.boot.autoconfigure.SpringBootApplication
- org.apache.avro.message.BinaryMessageDecoder
- api-gateway/mvnw
- category-service/mvnw
- mvnw
- product-service/mvnw
- .build
- product-query-service
- Graphify Rule Directive
- CQRS Architecture Rationale
- io.micrometer.tracing.Tracer
- org.springframework.stereotype.Service
- .build
- product_command_service_put_update route
- product_query_service_get_one route
- categories-topic configuration
- com.youssef.gamal.ecommerce.microservice:api-gateway
- com.youssef.gamal.ecommerce.microservice:category-service
- com.youssef.gamal.ecommerce.microservice:ecommerce-microservice
- com.youssef.gamal.ecommerce.microservice:product-service
- products-topic configuration
- API Gateway Service Overview
- Ecommerce Microservice Root Configuration
- 3. Component Details & Modifications
- Global Constraints

## God Nodes (most connected - your core abstractions)
1. `ProductEvent` - 58 edges
2. `CategoryEvent` - 55 edges
3. `Builder` - 51 edges
4. `Builder` - 39 edges
5. `Product` - 23 edges
6. `CategoryView` - 22 edges
7. `ProductView` - 22 edges
8. `Category` - 21 edges
9. `ProductMapper` - 13 edges
10. `CategoryDto` - 12 edges

## Surprising Connections (you probably didn't know these)
- `product_query_service_get_history route` --calls--> `Product Service Configuration`  [INFERRED]
  api-gateway/src/main/resources/application.yml → product-service/src/main/resources/application.yml
- `product_command_service_post_create route` --calls--> `Product Service Configuration`  [INFERRED]
  api-gateway/src/main/resources/application.yml → product-service/src/main/resources/application.yml
- `Category Service Configuration` --references--> `Zipkin Tracing service`  [INFERRED]
  category-service/src/main/resources/application.yml → compose.yml
- `Product Service Configuration` --references--> `Zipkin Tracing service`  [INFERRED]
  product-service/src/main/resources/application.yml → compose.yml
- `Category Service Configuration` --references--> `Kafka Broker service`  [INFERRED]
  category-service/src/main/resources/application.yml → compose.yml

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Event-Driven Kafka Messaging Cluster** — compose_broker, compose_schema_registry, product_service_src_main_resources_application_products_topic, category_service_src_main_resources_application_categories_topic [INFERRED 0.85]
- **CQRS Dual Data Store Setup** — product_service_compose_postgres, product_service_compose_mongodb, readme_cqrs_architecture [INFERRED 0.85]

## Communities (38 total, 11 thin omitted)

### Community 0 - "Product"
Cohesion: 0.13
Nodes (22): CategoryView, CategoryViewRepo, CategoryViewServiceImpl, Override, lombok.AllArgsConstructor, lombok.Builder, lombok.Data, lombok.NoArgsConstructor (+14 more)

### Community 2 - "ProductEvent"
Cohesion: 0.07
Nodes (6): ByteBuffer, DatumReader, DatumWriter, org.apache.avro.specific.AvroGenerated, Schema, ProductEvent

### Community 3 - "org.springframework.context.annotation.Configuration"
Cohesion: 0.09
Nodes (27): RateLimitingConfigs, Override, SecurityAuditorAware, CorsConfig, WebMvcConfigurer, JpaConfig, CachingConfigs, RedisTemplate (+19 more)

### Community 4 - "Builder"
Cohesion: 0.05
Nodes (4): Builder, org.apache.avro.specific.AvroGenerated, RecordBuilder, SpecificRecordBuilderBase

### Community 5 - "ProductMapper"
Cohesion: 0.08
Nodes (20): CategoryRepo, io.swagger.v3.oas.annotations.media.Schema, org.springframework.data.jpa.repository.JpaRepository, org.springframework.stereotype.Repository, PostMapping, PutMapping, ResponseStatus, RestController (+12 more)

### Community 6 - "Category"
Cohesion: 0.11
Nodes (17): CategoryController, PostMapping, PutMapping, RestController, Category, CategoryEventType, CREATED, DELETED (+9 more)

### Community 7 - "CategoryEvent"
Cohesion: 0.08
Nodes (8): Override, CategoryEvent, ByteBuffer, DatumReader, DatumWriter, Schema, SpecificRecord, SpecificRecordBase

### Community 8 - "lombok.extern.slf4j.Slf4j"
Cohesion: 0.10
Nodes (19): CategoryDto, CategoryViewController, CategoryViewMapper, CategoryEventConsumerServiceImpl, CategoryViewServiceIfc, jakarta.annotation.PostConstruct, lombok.extern.slf4j.Slf4j, lombok.RequiredArgsConstructor (+11 more)

### Community 9 - "Product Service Configuration"
Cohesion: 0.13
Nodes (16): api-gateway-redis service, product_command_service_post_create route, product_query_service_get_history route, mongodb-category-query service, postgresdb-category-command service, redis-cache-category-query service, Category Service Configuration, Kafka Broker service (+8 more)

### Community 10 - "org.springframework.boot.autoconfigure.SpringBootApplication"
Cohesion: 0.21
Nodes (5): ApiGatewayApplication, CategoryServiceApplication, org.springframework.boot.autoconfigure.SpringBootApplication, ProductServiceApplication, EcommerceMicroserviceApplication

### Community 11 - "org.apache.avro.message.BinaryMessageDecoder"
Cohesion: 0.17
Nodes (6): BinaryMessageDecoder, org.apache.avro.message.BinaryMessageDecoder, org.apache.avro.message.BinaryMessageEncoder, org.apache.avro.message.SchemaStore, org.apache.avro.specific.SpecificData, BinaryMessageDecoder

### Community 12 - "api-gateway/mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 13 - "category-service/mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 14 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 15 - "product-service/mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 16 - ".build"
Cohesion: 0.22
Nodes (4): ObjectInput, ObjectOutput, Override, SuppressWarnings

### Community 17 - "product-query-service"
Cohesion: 0.33
Nodes (7): API Gateway (Spring Cloud), Kafka, MongoDB, PostgreSQL, product-command-service, product-query-service, Redis

### Community 18 - "Graphify Rule Directive"
Cohesion: 0.50
Nodes (4): graphify query CLI, graphify update CLI, Graphify Rule Directive, Graphify Workflow

### Community 19 - "CQRS Architecture Rationale"
Cohesion: 0.67
Nodes (4): CQRS Architecture Rationale, Kafka Integration Pattern, Product Command Service Overview, Product Query Service Overview

### Community 20 - "io.micrometer.tracing.Tracer"
Cohesion: 0.16
Nodes (17): Override, TraceIdResponseHeaderFilter, Override, TraceIdResponseHeaderFilter, io.micrometer.tracing.Tracer, jakarta.servlet.FilterChain, jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse (+9 more)

### Community 21 - "org.springframework.stereotype.Service"
Cohesion: 0.31
Nodes (5): KafkaCategoryEventProducerImpl, org.springframework.kafka.core.KafkaTemplate, org.springframework.stereotype.Service, Override, KafkaProductEventProducerImpl

### Community 22 - ".build"
Cohesion: 0.22
Nodes (4): ObjectInput, ObjectOutput, Override, SuppressWarnings

### Community 36 - "3. Component Details & Modifications"
Cohesion: 0.15
Nodes (12): 1. Executive Summary, 2. Architecture & Service Overview, 3.1 `api-gateway` Dependencies (`api-gateway/pom.xml`), 3.2 Downstream Services OpenAPI Configuration, 3.3.1 Downstream OpenAPI Specs Routing, 3.3.2 Category Service Application Gateway Routes, 3.3.3 Centralized Swagger UI Configuration, 3.3 API Gateway Routing & Swagger UI Aggregation (`api-gateway/src/main/resources/application.yml`) (+4 more)

### Community 37 - "Global Constraints"
Cohesion: 0.29
Nodes (6): API Gateway Swagger Aggregation & Distributed Tracing Implementation Plan, Global Constraints, Task 1: Add WebFlux OpenAPI and Micrometer Tracing Dependencies to `api-gateway`, Task 2: Configure OpenAPI & Swagger Settings in Downstream Services, Task 3: Configure Category Service Routes, OpenAPI Spec Proxying, Swagger UI Aggregation, and Tracing in `api-gateway`, Task 4: End-to-End Build & Functional Verification

## Knowledge Gaps
- **45 isolated node(s):** `Task 1: Add WebFlux OpenAPI and Micrometer Tracing Dependencies to `api-gateway``, `Task 2: Configure OpenAPI & Swagger Settings in Downstream Services`, `Task 3: Configure Category Service Routes, OpenAPI Spec Proxying, Swagger UI Aggregation, and Tracing in `api-gateway``, `Task 4: End-to-End Build & Functional Verification`, `1. Executive Summary` (+40 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **11 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ProductEvent` connect `ProductEvent` to `Builder`, `ProductMapper`, `CategoryEvent`, `lombok.extern.slf4j.Slf4j`, `org.apache.avro.message.BinaryMessageDecoder`, `org.springframework.stereotype.Service`, `.build`?**
  _High betweenness centrality (0.206) - this node is a cross-community bridge._
- **Why does `CategoryEvent` connect `CategoryEvent` to `Builder`, `Category`, `lombok.extern.slf4j.Slf4j`, `org.apache.avro.message.BinaryMessageDecoder`, `.build`, `org.springframework.stereotype.Service`?**
  _High betweenness centrality (0.182) - this node is a cross-community bridge._
- **Why does `Builder` connect `Builder` to `ProductEvent`, `Builder`, `.build`?**
  _High betweenness centrality (0.118) - this node is a cross-community bridge._
- **What connects `Task 1: Add WebFlux OpenAPI and Micrometer Tracing Dependencies to `api-gateway``, `Task 2: Configure OpenAPI & Swagger Settings in Downstream Services`, `Task 3: Configure Category Service Routes, OpenAPI Spec Proxying, Swagger UI Aggregation, and Tracing in `api-gateway`` to the rest of the system?**
  _45 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Product` be split into smaller, more focused modules?**
  _Cohesion score 0.13240418118466898 - nodes in this community are weakly interconnected._
- **Should `Builder` be split into smaller, more focused modules?**
  _Cohesion score 0.043478260869565216 - nodes in this community are weakly interconnected._
- **Should `ProductEvent` be split into smaller, more focused modules?**
  _Cohesion score 0.06854838709677419 - nodes in this community are weakly interconnected._