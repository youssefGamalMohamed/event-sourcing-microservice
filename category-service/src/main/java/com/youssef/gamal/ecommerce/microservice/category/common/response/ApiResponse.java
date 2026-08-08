package com.youssef.gamal.ecommerce.microservice.category.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Instant timestamp;
    private int status;
    private String message;
    private T data;
    private String path;

    public static <T> ApiResponse<T> success(T data, int status, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message("Operation completed successfully")
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message, int status, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message(message)
                .data(data)
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message, String path) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message(message)
                .data(null)
                .path(path)
                .build();
    }
}
