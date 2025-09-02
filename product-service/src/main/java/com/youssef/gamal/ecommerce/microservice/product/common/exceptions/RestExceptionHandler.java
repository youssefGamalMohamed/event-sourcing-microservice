package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.exceptions.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Handling generic exception:", ex);
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ProductServiceErrorsEnum.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        log.error("Handling NoSuchElementException:", ex);
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                ProductServiceErrorsEnum.PRODUCT_NOT_FOUND,
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status,
            ProductServiceErrorsEnum commandError,
            String path) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(commandError.getServiceErrorMessage())
                .serviceCode(String.valueOf(commandError.getServiceErrorCode()))
                .path(path)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
