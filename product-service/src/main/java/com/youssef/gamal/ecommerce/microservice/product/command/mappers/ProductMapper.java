package com.youssef.gamal.ecommerce.microservice.product.command.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands.ProductCommandDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.shared.events.*;
import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductCommandDto toDto(Product product);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductCommandDto productDto);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFrom(Product source, @MappingTarget Product target);

    
    // ✅ Explicitly map LocalDateTime → Instant using conversion functions
    @Mapping(target = "creationDate", expression = "java(map(product.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(product.getLastModifiedDate()))")
    @Mapping(target = "eventType", source = "eventType")
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
