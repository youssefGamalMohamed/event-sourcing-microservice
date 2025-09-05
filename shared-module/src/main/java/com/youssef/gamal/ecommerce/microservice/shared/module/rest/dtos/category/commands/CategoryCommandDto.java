package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(
        name = "CategoryCommandDto",
        description = "DTO representing a category for create/update operations"
)
public record CategoryCommandDto(

        @Schema(
                description = "Unique identifier of the category",
                example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
        )
        String id,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name must be at most 255 characters")
        @Schema(
                description = "Name of the category",
                example = "Electronics"
        )
        String name,

        @Schema(
                description = "Date and time when the category was created",
                example = "2025-09-05T10:30:00"
        )
        LocalDateTime creationDate,

        @Schema(
                description = "User who created the category",
                example = "admin"
        )
        String createdBy,

        @Schema(
                description = "Date and time when the category was last modified",
                example = "2025-09-05T10:30:00"
        )
        LocalDateTime lastModifiedDate,

        @Schema(
                description = "User who last modified the category",
                example = "admin"
        )
        String lastModifiedBy

) implements Serializable {
}
