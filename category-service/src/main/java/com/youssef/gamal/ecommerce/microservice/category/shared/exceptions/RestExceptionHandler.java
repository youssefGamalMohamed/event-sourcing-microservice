package com.youssef.gamal.ecommerce.microservice.category.shared.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.youssef.gamal.ecommerce.microservice.category.shared.constants.LogErrorMessagesConstants.PLEASE_TRY_AGAIN_LATER;
import static com.youssef.gamal.ecommerce.microservice.category.shared.constants.LogErrorMessagesConstants.UNABLE_TO_PROCESS_REQUEST_SYSTEM_CURRENTLY_UNAVAILABLE;
import static com.youssef.gamal.ecommerce.microservice.category.shared.constants.ServiceErrorCodesConstants.ERROR_CODE_MAX_RETRIES_FAILED_ON_KAFKA;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @Value("${retry.exception.event-publish-retry-after:30}")
    private int eventPublishRetryAfter;

    @Value("${retry.exception.exhausted-retry-after:60}")
    private int exhaustedRetryAfter;


    @ExceptionHandler(EventPublishException.class)
    public ResponseEntity<ServiceUnavailableResponse> handleEventPublishException(
            EventPublishException ex, HttpServletRequest request) {

        log.error("Handling EventPublishException:", ex);

        ServiceUnavailableResponse response = ServiceUnavailableResponse.builder()
                .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
                .detailedMessage(UNABLE_TO_PROCESS_REQUEST_SYSTEM_CURRENTLY_UNAVAILABLE)
                .userMessage(PLEASE_TRY_AGAIN_LATER)
                .retryAfter(eventPublishRetryAfter)  // ✅ From @Value
                .serviceErrorCode(ERROR_CODE_MAX_RETRIES_FAILED_ON_KAFKA)
                .build();

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header(HttpHeaders.RETRY_AFTER, String.valueOf(eventPublishRetryAfter))
                .body(response);
    }

    @ExceptionHandler(ExhaustedRetryException.class)
    public ResponseEntity<ServiceUnavailableResponse> handleExhaustedRetryException(
            ExhaustedRetryException ex, HttpServletRequest request) {

        log.error("Handling ExhaustedRetryException:", ex);

        ServiceUnavailableResponse response = ServiceUnavailableResponse.builder()
                .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
                .detailedMessage(UNABLE_TO_PROCESS_REQUEST_SYSTEM_CURRENTLY_UNAVAILABLE)
                .userMessage(PLEASE_TRY_AGAIN_LATER)
                .retryAfter(exhaustedRetryAfter)  // ✅ From @Value
                .serviceErrorCode(ERROR_CODE_MAX_RETRIES_FAILED_ON_KAFKA)
                .build();

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .header(HttpHeaders.RETRY_AFTER, String.valueOf(exhaustedRetryAfter))
                .body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalServerErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Handling generic exception:", ex);

        InternalServerErrorResponse response = InternalServerErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .detailedMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<NotFoundResponse> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
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
    public ResponseEntity<ConflictErrorResponse> handleCategoryAlreadyExistException(
            AlreadyExistException ex, HttpServletRequest request) {
        log.error("Handling CategoryAlreadyExistException:", ex);

        ConflictErrorResponse response = ConflictErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .detailedMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
