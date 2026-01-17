package com.youssef.gamal.ecommerce.microservice.product.command.mappers;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Category;
import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.CategoryRef;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    
    CategoryRef toEventRef(Category category);

    
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryQueryResponse categoryQueryResponse);
    
    Set<Category> toEntity(Set<CategoryQueryResponse> categoryQueryResponses);
}
