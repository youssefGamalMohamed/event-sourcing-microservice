package com.youssef.gamal.ecommerce.microservice.category.commands.mappers;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.models.CreateCommand;
import com.youssef.gamal.ecommerce.microservice.category.commands.models.UpdateCommand;
import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

	@Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "version", ignore = true)
    Category toEntity(CreateCommand categoryCommand);

	@Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
	@Mapping(target = "version", ignore = true)
    Category toEntity(UpdateCommand categoryCommand);
	

    CreateCommand toCreateCommandResponse(Category category);
   
    UpdateCommand toUpdateCommand(Category category);
   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    void updateFrom(Category source, @MappingTarget Category target);


    // ✅ Explicitly map LocalDateTime → Instant using conversion functions
    @Mapping(target = "originalId", source = "category.id")
    @Mapping(target = "snapshotId", constant = "EMPTY")
    @Mapping(target = "creationDate", expression = "java(map(category.getCreationDate()))")
    @Mapping(target = "lastModifiedDate", expression = "java(map(category.getLastModifiedDate()))")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now().toEpochMilli())")
    CategoryEvent toEvent(Category category, String eventType);

    // === Conversion methods ===
    default Instant map(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDateTime map(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }
}
