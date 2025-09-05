package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "Represents a product's view model.")
public record ProductViewDto(

        @Schema(description = "The unique identifier of the product.", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotBlank(message = "Product ID cannot be blank.")
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

        @Schema(description = "The date and time the product was created.", example = "2025-09-06T10:00:00Z")
        LocalDateTime creationDate,

        @Schema(description = "The user who created the product.", example = "JohnDoe")
        String createdBy,

        @Schema(description = "The date and time the product was last modified.", example = "2025-09-06T11:30:00Z")
        LocalDateTime lastModifiedDate,

        @Schema(description = "The user who last modified the product.", example = "JaneSmith")
        String lastModifiedBy,

        @Schema(description = "The type of event associated with the product.", example = "CREATED")
        String eventType
) implements Serializable {
}