package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.query;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ProductViewDto(
        String id,
        String name,
        String description,
        double price,
        int quantity,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy,
        String eventType
) implements Serializable {
}
