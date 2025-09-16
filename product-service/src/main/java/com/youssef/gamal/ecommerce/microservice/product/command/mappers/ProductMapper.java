package com.youssef.gamal.ecommerce.microservice.product.command.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommand;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands.ProductCommand;

@Mapper(
		componentModel = "spring" ,
		uses = { CategoryMapper.class }   // ✅ Reuse CategoryMapper here
)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mapping(target = "categoriesQueriesResponse", source = "categoryQueryResponses")
    ProductCommand toDto(Product product, Set<CategoryQueryResponse> categoryQueryResponses);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", source = "categoriesQueriesResponse") // ✅ Map Set<CategoryCommand> → Set<String>
    Product toEntity(ProductCommand productDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFrom(Product source, @MappingTarget Product target);


    // ✅ Explicitly map LocalDateTime → Instant using conversion functions
    @Mapping(target = "originalId", source = "product.id")
    @Mapping(target = "snapshotId", constant = "EMPTY")
    @Mapping(target = "categoryIds", ignore = true)
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "categories", source = "product.categories")
    @Mapping(target = "creationDate", expression = "java(map(product.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(product.getLastModifiedDate()))")
    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now().toEpochMilli())")
    ProductEvent toEvent(Product product, String eventType);

    // === Conversion methods ===
    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}
