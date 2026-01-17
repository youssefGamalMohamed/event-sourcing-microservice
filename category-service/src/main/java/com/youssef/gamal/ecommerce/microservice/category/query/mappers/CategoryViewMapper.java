package com.youssef.gamal.ecommerce.microservice.category.query.mappers;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring", imports = {CategoryQueryResponse.class})
public interface CategoryViewMapper {

    CategoryViewMapper INSTANCE = Mappers.getMapper(CategoryViewMapper.class);

    // ✅ CategoryEvent → CategoryView
    @Mapping(target = "snapshotId", ignore = true)
    @Mapping(target = "originalId", source = "originalId")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creationDate", expression = "java(map(categoryEvent.getCreationDate()))")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "lastModifiedDate", expression = "java(map(categoryEvent.getLastModifiedDate()))")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    CategoryView toView(CategoryEvent categoryEvent);

    // ✅ CategoryView → CategoryQueryResponse
    @Mapping(target = "originalId", source = "originalId")
    @Mapping(target = "snapshotId", source = "snapshotId")
    CategoryQueryResponse toDto(CategoryView categoryView);

    // === Conversion methods ===
    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }
}
