package com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String serviceCode;
    private String path;
    private String message;
}