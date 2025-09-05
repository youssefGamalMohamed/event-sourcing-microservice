package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CategoryCommandDto(
        String id,
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy
) implements Serializable {
}