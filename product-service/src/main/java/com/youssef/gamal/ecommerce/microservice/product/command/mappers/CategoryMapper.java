package com.youssef.gamal.ecommerce.microservice.product.command.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Category;
import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.CategoryRef;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommand;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    
    
    CategoryRef toEventRef(Category category);
    
    CategoryCommand toDto(CategoryCommand categoryCommand);
    

    Set<CategoryCommand> toDtos(Set<CategoryCommand> categoryCommands);
    
    default String toEntity(CategoryCommand categoryCommand) {
    	return categoryCommand.id();
    }

    default Set<String> toEntities(Set<CategoryCommand> categoryCommands) {
    	return categoryCommands == null ? new HashSet<>() :
								            categoryCommands.stream()
								                    .map(this::toEntity)
								                    .collect(Collectors.toSet());
    }
}
