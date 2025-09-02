package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands;


import java.io.Serializable;
import java.time.LocalDateTime;

public record ProductCommandDto(
        String id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy
) implements Serializable {
}
