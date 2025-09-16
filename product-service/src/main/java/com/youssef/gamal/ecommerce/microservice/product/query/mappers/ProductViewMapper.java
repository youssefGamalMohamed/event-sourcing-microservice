package com.youssef.gamal.ecommerce.microservice.product.query.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.query.ProducQueryResponse;

@Mapper(
		componentModel = "spring" ,
		uses = { CategoryViewMapper.class }
)
public interface ProductViewMapper {

    ProductViewMapper INSTANCE = Mappers.getMapper(ProductViewMapper.class);
    
    // ✅ ProductEvent → ProductView
    @Mapping(target = "snapshotId", ignore = true)
    @Mapping(target = "originalId", source = "originalId")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "creationDate", expression = "java(map(productEvent.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(productEvent.getLastModifiedDate()))")
    ProductView toProductView(ProductEvent productEvent);

    // ✅ ProductView → ProducQueryResponse
    @Mapping(target = "catoegoriesViews", source = "categories")
    ProducQueryResponse toDto(ProductView productView);

    // === Conversion methods ===
    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }
    
}
