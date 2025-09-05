package com.youssef.gamal.ecommerce.microservice.category.common.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ErrorResponse {

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2025-09-05T10:30:00"
    )
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(
            description = "Detailed message describing the error",
            example = "contains detailed error message about the issue"
    )
    private String detailedMessage;
}
