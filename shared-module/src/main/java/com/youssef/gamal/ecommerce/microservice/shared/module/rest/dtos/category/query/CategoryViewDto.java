package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for transferring category view data.
 */
public record CategoryViewDto(
        String id,
        String originalId,
        String name,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy,
        String eventType
) implements Serializable {}

