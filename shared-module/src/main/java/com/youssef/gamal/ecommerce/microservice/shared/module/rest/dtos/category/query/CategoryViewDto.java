package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for transferring category view data.
 */
@Schema(
        name = "CategoryViewDto",
        description = "Represents the category view data returned by query endpoints."
)
public record CategoryViewDto(

        @Schema(description = "Unique identifier of the category (UUID or database ID)",
                example = "68bb0f685176c61054f6e497")
        String id,

        @Schema(description = "Original ID used for correlation with other services or systems",
                example = "e96ec223-6318-43de-bfb6-7db7986f1216")
        String originalId,

        @Schema(description = "Human-readable category name",
                example = "Electronics")
        String name,

        @Schema(description = "Timestamp when the category was created",
                example = "2025-01-01T12:00:00")
        LocalDateTime creationDate,

        @Schema(description = "User or system that created the category",
                example = "system")
        String createdBy,

        @Schema(description = "Timestamp when the category was last updated",
                example = "2025-02-01T09:30:00")
        LocalDateTime lastModifiedDate,

        @Schema(description = "User or system that last updated the category",
                example = "adminUser")
        String lastModifiedBy,

        @Schema(description = "Type of event that last affected this category",
                example = "CREATED")
        String eventType
) implements Serializable {}
