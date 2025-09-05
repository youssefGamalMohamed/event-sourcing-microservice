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
        name = "ConflictErrorResponse",
        description = "Response returned when a conflict occurs, e.g., product name already exists"
)
public class ConflictErrorResponse extends ErrorResponse {

    @Builder.Default
    @Schema(
            description = "HTTP status for the error",
            example = "CONFLICT"
    )
    private HttpStatus httpStatus = HttpStatus.CONFLICT;

    @Builder.Default
    @Schema(
            description = "Specific service error code",
            example = ServiceErrorCodesConstants.PRODUCT_ALREADY_EXISTS
    )
    private String serviceErrorCode = ServiceErrorCodesConstants.PRODUCT_ALREADY_EXISTS;
}
