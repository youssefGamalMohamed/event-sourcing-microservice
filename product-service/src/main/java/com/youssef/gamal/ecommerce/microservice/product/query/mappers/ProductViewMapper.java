package com.youssef.gamal.ecommerce.microservice.product.query.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.query.ProductViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;

@Mapper(componentModel = "spring")
public interface ProductViewMapper {
    
    ProductViewMapper INSTANCE = Mappers.getMapper(ProductViewMapper.class);

    // ✅ ProductEvent → ProductView
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "originalId", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "creationDate", expression = "java(map(productEvent.getCreationDate()))")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "lastModifiedDate", expression = "java(map(productEvent.getLastModifiedDate()))")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    ProductView toProductView(ProductEvent productEvent);

    // ✅ ProductView → ProductViewDto
    @Mapping(target = "id", source = "productView.originalId")
    ProductViewDto toDto(ProductView productView);

    // === Conversion methods ===
    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }
}
