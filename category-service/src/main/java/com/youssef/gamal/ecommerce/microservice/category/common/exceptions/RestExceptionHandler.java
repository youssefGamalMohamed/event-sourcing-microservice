package com.youssef.gamal.ecommerce.microservice.category.common.exceptions;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.exceptions.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Handling generic exception:", ex);
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                CategoryServiceErrorsEnum.INTERNAL_SERVER_ERROR,
                CategoryServiceErrorsEnum.INTERNAL_SERVER_ERROR.toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        log.error("Handling NoSuchElementException:", ex);
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                CategoryServiceErrorsEnum.CATEGORY_NOT_FOUND,
                CategoryServiceErrorsEnum.CATEGORY_NOT_FOUND.toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Handling validation exception:", ex);

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));


        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                CategoryServiceErrorsEnum.CATEGORY_REQUEST_BODY_FAILED_VALIDATION,
                message,
                request.getRequestURI()
        );
    }


    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistException(
            CategoryAlreadyExistException ex, HttpServletRequest request) {
        log.error("Handling CategoryAlreadyExistException:", ex);

        return createErrorResponse(
                HttpStatus.CONFLICT, // 409
                CategoryServiceErrorsEnum.CATEGORY_NAME_ALREADY_EXISTS,
                ex.getMessage(), // "Category Already Exists with Name = X"
                request.getRequestURI()
        );
    }


    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status,
            CategoryServiceErrorsEnum errorsEnum,
            String detailedMessage,
            String path
    ) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(errorsEnum.getServiceErrorMessage())
                .detailedMessage(detailedMessage)
                .serviceCode(String.valueOf(errorsEnum.getServiceErrorCode()))
                .path(path)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
