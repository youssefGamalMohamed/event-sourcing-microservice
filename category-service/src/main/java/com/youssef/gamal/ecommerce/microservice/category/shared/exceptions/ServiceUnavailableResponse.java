package com.youssef.gamal.ecommerce.microservice.category.shared.exceptions;

import com.youssef.gamal.ecommerce.microservice.category.shared.constants.ServiceErrorCodesConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@Schema(
        name = "ServiceUnavailableResponse",
        description = "Response returned when a service is temporarily unavailable"
)
public class ServiceUnavailableResponse extends ErrorResponse {

    @Builder.Default
    @Schema(
            description = "HTTP status for the error",
            example = "SERVICE_UNAVAILABLE"
    )
    private HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;

    @Schema(
            description = "User-friendly message for the client",
            example = "Please try again in a few moments."
    )
    private String userMessage;

    @Schema(
            description = "Suggested time in seconds before retrying the request",
            example = "30"
    )
    private Integer retryAfter;

    @Schema(
            description = "Error code for categorizing the error",
            example = ServiceErrorCodesConstants.ERROR_CODE_MAX_RETRIES_FAILED_ON_KAFKA
    )
    private String serviceErrorCode;
}