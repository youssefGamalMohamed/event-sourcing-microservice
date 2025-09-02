package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CategoryCommandDto (
        String id,
        String name,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy

) implements Serializable {
}