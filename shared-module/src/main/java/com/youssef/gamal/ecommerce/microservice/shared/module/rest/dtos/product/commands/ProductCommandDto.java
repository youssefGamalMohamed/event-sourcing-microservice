package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "Represents the data transfer object for creating and updating a product.")
public record ProductCommandDto(

        // This field is typically not provided by the client for creation
        // and is ignored for updates, but included for the response DTO.
        @Schema(description = "The unique identifier of the product.", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "The name of the product.", example = "Laptop")
        @NotBlank(message = "Product name cannot be blank.")
        @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters.")
        String name,

        @Schema(description = "A brief description of the product.", example = "A powerful and lightweight laptop for professional use.")
        @Size(max = 500, message = "Product description cannot exceed 500 characters.")
        String description,

        @Schema(description = "The price of the product.", example = "1200.50")
        @NotNull(message = "Product price cannot be null.")
        @Min(value = 0, message = "Product price cannot be negative.")
        double price,

        @Schema(description = "The available quantity of the product.", example = "50")
        @NotNull(message = "Product quantity cannot be null.")
        @Min(value = 0, message = "Product quantity cannot be negative.")
        int quantity,

        // Audit fields are automatically managed by the system and are not validated from the client.
        @Schema(description = "The date and time the product was created.", example = "2025-09-06T10:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime creationDate,

        @Schema(description = "The user who created the product.", example = "JohnDoe", accessMode = Schema.AccessMode.READ_ONLY)
        String createdBy,

        @Schema(description = "The date and time the product was last modified.", example = "2025-09-06T11:30:00Z", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime lastModifiedDate,

        @Schema(description = "The user who last modified the product.", example = "JaneSmith", accessMode = Schema.AccessMode.READ_ONLY)
        String lastModifiedBy
) implements Serializable {
}
