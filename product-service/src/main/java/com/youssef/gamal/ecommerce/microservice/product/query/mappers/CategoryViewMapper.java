package com.youssef.gamal.ecommerce.microservice.product.query.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.CategoryRef;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

@Mapper(
		componentModel = "spring" 
)
public interface CategoryViewMapper {

    CategoryViewMapper INSTANCE = Mappers.getMapper(CategoryViewMapper.class);
    
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "eventType", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "name", ignore = true)
    CategoryView toEntity(CategoryRef categoryRef);
    
    CategoryView toEntity(CategoryQueryResponse dto);
    
    Set<CategoryView> toEntities(Set<CategoryQueryResponse> dtos);
    
}
