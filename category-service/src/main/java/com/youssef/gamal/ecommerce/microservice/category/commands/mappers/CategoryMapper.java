package com.youssef.gamal.ecommerce.microservice.category.commands.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.youssef.gamal.ecommerce.microservice.category.commands.dtos.CategoryDto;
import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.shared.events.CategoryEvent;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
	Category toEntity(CategoryDto categoryDto);
	

	CategoryDto toDto(Category categoryDto);
	
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFrom(Category source, @MappingTarget Category target);
    
    
    
    // ✅ Explicitly map LocalDateTime → Instant using conversion functions
    @Mapping(target = "creationDate", expression = "java(map(product.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(product.getLastModifiedDate()))")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now().toEpochMilli())")
    CategoryEvent toEvent(Category product, String eventType);

    // === Conversion methods ===
    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}
