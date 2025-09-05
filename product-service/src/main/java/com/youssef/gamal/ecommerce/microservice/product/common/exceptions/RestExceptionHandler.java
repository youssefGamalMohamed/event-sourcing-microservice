package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalServerErrorResponse handleException(Exception ex, HttpServletRequest request) {
        log.error("Handling generic exception:", ex);
        return InternalServerErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .detailedMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundResponse handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        log.error("Handling NoSuchElementException:", ex);
        return NotFoundResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .detailedMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Handling validation exception:", ex);

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));


        return ValidationErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .detailedMessage(message)
                .build();
    }


    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ConflictErrorResponse handleCategoryAlreadyExistException(
            AlreadyExistException ex, HttpServletRequest request) {
        log.error("Handling Product AlreadyExistException:", ex);

        return ConflictErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .detailedMessage(ex.getMessage())
                .build();
    }
}
