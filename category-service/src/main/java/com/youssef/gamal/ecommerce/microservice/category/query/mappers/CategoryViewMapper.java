package com.youssef.gamal.ecommerce.microservice.category.query.mappers;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring")
public interface CategoryViewMapper {

    CategoryViewMapper INSTANCE = Mappers.getMapper(CategoryViewMapper.class);

    // ✅ CategoryEvent → CategoryView
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "originalId", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "creationDate", expression = "java(map(categoryEvent.getCreationDate()))")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "lastModifiedDate", expression = "java(map(categoryEvent.getLastModifiedDate()))")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    CategoryView toView(CategoryEvent categoryEvent);

    // ✅ CategoryView → CategoryViewDto
    @Mapping(target = "id", source = "categoryView.originalId")
    CategoryViewDto toDto(CategoryView categoryView);

    // === Conversion methods ===
    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }
}
