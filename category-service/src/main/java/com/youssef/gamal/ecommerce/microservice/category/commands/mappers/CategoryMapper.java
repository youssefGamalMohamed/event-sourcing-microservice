package com.youssef.gamal.ecommerce.microservice.category.commands.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.youssef.gamal.ecommerce.microservice.category.commands.dtos.CategoryDto;
import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;

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
}
