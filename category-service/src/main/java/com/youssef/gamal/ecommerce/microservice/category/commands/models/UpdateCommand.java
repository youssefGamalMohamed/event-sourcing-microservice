package com.youssef.gamal.ecommerce.microservice.category.commands.models;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(
        name = "UpdateCommand",
        description = "DTO representing a category for update operation"
)
public record UpdateCommand(

        @Schema(
                description = "Unique identifier of the category",
                example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                accessMode = AccessMode.READ_ONLY
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
                example = "2025-09-05T10:30:00",
                accessMode = AccessMode.READ_ONLY
        )
        LocalDateTime creationDate,

        @Schema(
                description = "User who created the category",
                example = "admin",
                accessMode = AccessMode.READ_ONLY
        )
        String createdBy,

        @Schema(
                description = "Date and time when the category was last modified",
                example = "2025-09-05T10:30:00",
                accessMode = AccessMode.READ_ONLY
        )
        
        LocalDateTime lastModifiedDate,

        @Schema(
                description = "User who last modified the category",
                example = "admin",
                accessMode = AccessMode.READ_ONLY
        )
        String lastModifiedBy

) implements Serializable {
}
