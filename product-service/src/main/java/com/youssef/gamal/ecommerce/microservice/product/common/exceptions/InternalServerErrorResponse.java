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
        name = "InternalServerErrorResponse",
        description = "Response returned when the server encounters an unexpected error"
)
public class InternalServerErrorResponse extends ErrorResponse {

    @Builder.Default
    @Schema(
            description = "HTTP status for the error",
            example = "INTERNAL_SERVER_ERROR"
    )
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    @Builder.Default
    @Schema(
            description = "Specific service error code",
            example = ServiceErrorCodesConstants.INTERNAL_SERVER_ERROR
    )
    private String serviceErrorCode = ServiceErrorCodesConstants.INTERNAL_SERVER_ERROR;
}
