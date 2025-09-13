package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalServerErrorResponse> handleException(
            Exception ex, HttpServletRequest request) {
        log.error("Handling generic exception:", ex);

        InternalServerErrorResponse response = InternalServerErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .detailedMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<NotFoundResponse> handleNoSuchElementException(
            NoSuchElementException ex, HttpServletRequest request) {
        log.error("Handling NoSuchElementException:", ex);

        NotFoundResponse response = NotFoundResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .detailedMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Handling validation exception:", ex);

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .detailedMessage(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ConflictErrorResponse> handleAlreadyExistException(
            AlreadyExistException ex, HttpServletRequest request) {
        log.error("Handling Product AlreadyExistException:", ex);

        ConflictErrorResponse response = ConflictErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .detailedMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
