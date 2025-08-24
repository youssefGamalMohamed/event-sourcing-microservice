package com.youssef.gamal.ecommerce.microservice.product.command.dtos;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

//@Builder
@Schema(description = "Product Data Transfer Object", name = "Product")
public record ProductDto(
        String id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy
) {
}
