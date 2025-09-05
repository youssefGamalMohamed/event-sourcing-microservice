package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;

import com.youssef.gamal.ecommerce.microservice.product.common.constants.ServiceErrorCodesConstants;
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
        name = "ValidationErrorResponse",
        description = "Response returned when the request body fails validation"
)
public class ValidationErrorResponse extends ErrorResponse {

    @Builder.Default
    @Schema(
            description = "HTTP status for the validation error",
            example = "BAD_REQUEST"
    )
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @Builder.Default
    @Schema(
            description = "Specific service error code for validation failures",
            example = ServiceErrorCodesConstants.PRODUCT_REQUEST_BODY_FAILED_VALIDATION
    )
    private String serviceErrorCode = ServiceErrorCodesConstants.PRODUCT_REQUEST_BODY_FAILED_VALIDATION;
}
