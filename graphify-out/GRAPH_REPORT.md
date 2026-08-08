# Graph Report - .  (2026-08-08)

## Corpus Check
- Corpus is ~25,986 words - fits in a single context window. You may not need a graph.

## Summary
- 496 nodes · 940 edges · 36 communities (22 shown, 14 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 54 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Category Service Domain & Query Views
- Product Avro Event Builder
- Product Kafka Event Publisher
- API Gateway & Security Auditing
- Category Avro Event Builder
- MapStruct Data Mappers
- Category Event Enums & Types
- Category Event Producer Service
- Category Command REST Controller
- Docker Infrastructure & Persistence Stores
- Spring Boot Microservice Applications
- Category Avro Decoder & Serializers
- API Gateway Maven Wrapper
- Category Service Maven Wrapper
- Root Maven Wrapper
- Product Service Maven Wrapper
- Category Avro Event Storage Methods
- Event Sourcing Architecture Diagram
- Graphify Knowledge Graph Instructions
- CQRS & Event Sourcing Documentation
- Category Kafka Event Publisher Impl
- Category Avro ByteBuffer Conversion
- Avro SpecificData Record Utilities
- API Gateway Update Route Config
- API Gateway Get Route Config
- Category Kafka Topic Config
- API Gateway Package Space
- Category Service Package Space
- Ecommerce Microservice Root Package
- Product Service Package Space
- Product Kafka Topic Config
- API Gateway Overview Doc
- Ecommerce Root Application Config

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

## Communities (36 total, 14 thin omitted)

### Community 0 - "Category Service Domain & Query Views"
Cohesion: 0.07
Nodes (36): Category, CategoryViewController, CategoryViewMapper, CategoryView, CategoryViewRepo, CategoryEventConsumerServiceImpl, CategoryViewServiceIfc, CategoryViewServiceImpl (+28 more)

### Community 2 - "Product Kafka Event Publisher"
Cohesion: 0.05
Nodes (11): Override, ByteBuffer, DatumReader, DatumWriter, ObjectInput, ObjectOutput, org.apache.avro.specific.AvroGenerated, Override (+3 more)

### Community 3 - "API Gateway & Security Auditing"
Cohesion: 0.11
Nodes (22): RateLimitingConfigs, Override, SecurityAuditorAware, JpaConfig, CachingConfigs, RedisTemplate, MongoDBConfigs, org.springframework.cache.annotation.EnableCaching (+14 more)

### Community 4 - "Category Avro Event Builder"
Cohesion: 0.05
Nodes (4): Builder, org.apache.avro.specific.AvroGenerated, RecordBuilder, SpecificRecordBuilderBase

### Community 5 - "MapStruct Data Mappers"
Cohesion: 0.11
Nodes (16): io.swagger.v3.oas.annotations.media.Schema, org.mapstruct.Mapper, org.mapstruct.Mapping, PostMapping, PutMapping, ResponseStatus, RestController, ProductController (+8 more)

### Community 6 - "Category Event Enums & Types"
Cohesion: 0.12
Nodes (21): CategoryEventType, CREATED, DELETED, UPDATED, CategoryRepo, CategoryEventProducerIfc, CategoryServiceImpl, KafkaCategoryEventProducerImpl (+13 more)

### Community 7 - "Category Event Producer Service"
Cohesion: 0.09
Nodes (6): CategoryEvent, DatumReader, DatumWriter, Schema, SpecificRecord, SpecificRecordBase

### Community 8 - "Category Command REST Controller"
Cohesion: 0.14
Nodes (9): CategoryController, PostMapping, PutMapping, RestController, CategoryDto, CategoryMapper, CategoryServiceIfc, Override (+1 more)

### Community 9 - "Docker Infrastructure & Persistence Stores"
Cohesion: 0.13
Nodes (16): api-gateway-redis service, product_command_service_post_create route, product_query_service_get_history route, mongodb-category-query service, postgresdb-category-command service, redis-cache-category-query service, Category Service Configuration, Kafka Broker service (+8 more)

### Community 10 - "Spring Boot Microservice Applications"
Cohesion: 0.21
Nodes (5): ApiGatewayApplication, CategoryServiceApplication, org.springframework.boot.autoconfigure.SpringBootApplication, ProductServiceApplication, EcommerceMicroserviceApplication

### Community 11 - "Category Avro Decoder & Serializers"
Cohesion: 0.21
Nodes (5): BinaryMessageDecoder, org.apache.avro.message.BinaryMessageDecoder, org.apache.avro.message.BinaryMessageEncoder, org.apache.avro.message.SchemaStore, BinaryMessageDecoder

### Community 12 - "API Gateway Maven Wrapper"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 13 - "Category Service Maven Wrapper"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 14 - "Root Maven Wrapper"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 15 - "Product Service Maven Wrapper"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 16 - "Category Avro Event Storage Methods"
Cohesion: 0.22
Nodes (4): ObjectInput, ObjectOutput, Override, SuppressWarnings

### Community 17 - "Event Sourcing Architecture Diagram"
Cohesion: 0.33
Nodes (7): API Gateway (Spring Cloud), Kafka, MongoDB, PostgreSQL, product-command-service, product-query-service, Redis

### Community 18 - "Graphify Knowledge Graph Instructions"
Cohesion: 0.50
Nodes (4): graphify query CLI, graphify update CLI, Graphify Rule Directive, Graphify Workflow

### Community 19 - "CQRS & Event Sourcing Documentation"
Cohesion: 0.67
Nodes (4): CQRS Architecture Rationale, Kafka Integration Pattern, Product Command Service Overview, Product Query Service Overview

## Knowledge Gaps
- **32 isolated node(s):** `com.youssef.gamal.ecommerce.microservice:api-gateway`, `com.youssef.gamal.ecommerce.microservice:category-service`, `CREATED`, `UPDATED`, `DELETED` (+27 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **14 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ProductEvent` connect `Product Kafka Event Publisher` to `Category Service Domain & Query Views`, `Product Avro Event Builder`, `MapStruct Data Mappers`, `Category Event Enums & Types`, `Category Event Producer Service`, `Category Avro Decoder & Serializers`, `Avro SpecificData Record Utilities`?**
  _High betweenness centrality (0.248) - this node is a cross-community bridge._
- **Why does `CategoryEvent` connect `Category Event Producer Service` to `Category Service Domain & Query Views`, `Category Avro Event Builder`, `MapStruct Data Mappers`, `Category Event Enums & Types`, `Category Command REST Controller`, `Category Avro Decoder & Serializers`, `Category Avro Event Storage Methods`, `Category Kafka Event Publisher Impl`, `Category Avro ByteBuffer Conversion`, `Avro SpecificData Record Utilities`?**
  _High betweenness centrality (0.219) - this node is a cross-community bridge._
- **Why does `Builder` connect `Product Avro Event Builder` to `Product Kafka Event Publisher`, `Category Avro Event Builder`?**
  _High betweenness centrality (0.143) - this node is a cross-community bridge._
- **What connects `com.youssef.gamal.ecommerce.microservice:api-gateway`, `com.youssef.gamal.ecommerce.microservice:category-service`, `CREATED` to the rest of the system?**
  _32 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Category Service Domain & Query Views` be split into smaller, more focused modules?**
  _Cohesion score 0.06905671466353218 - nodes in this community are weakly interconnected._
- **Should `Product Avro Event Builder` be split into smaller, more focused modules?**
  _Cohesion score 0.043478260869565216 - nodes in this community are weakly interconnected._
- **Should `Product Kafka Event Publisher` be split into smaller, more focused modules?**
  _Cohesion score 0.05391120507399577 - nodes in this community are weakly interconnected._