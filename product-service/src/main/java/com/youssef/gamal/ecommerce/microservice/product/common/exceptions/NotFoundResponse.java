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
        name = "NotFoundResponse",
        description = "Response returned when a requested resource is not found"
)
public class NotFoundResponse extends ErrorResponse {

    @Builder.Default
    @Schema(
            description = "HTTP status for the error",
            example = "NOT_FOUND"
    )
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    @Builder.Default
    @Schema(
            description = "Specific service error code",
            example = ServiceErrorCodesConstants.PRODUCT_NOT_FOUND
    )
    private String serviceErrorCode = ServiceErrorCodesConstants.PRODUCT_NOT_FOUND;
}
