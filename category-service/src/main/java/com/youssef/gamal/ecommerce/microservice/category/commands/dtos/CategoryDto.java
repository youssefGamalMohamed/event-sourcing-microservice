package com.youssef.gamal.ecommerce.microservice.category.commands.dtos;

import java.time.LocalDateTime;

public record CategoryDto(
		String id,
		String name,
        LocalDateTime creationDate,
        String createdBy,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy,
        String eventType
) {

}
