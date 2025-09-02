package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductServiceErrorsEnum {

    INTERNAL_SERVER_ERROR(1001, "An unexpected error occurred. Please contact support."),
    PRODUCT_NOT_FOUND(1002, "The requested Product could not be found.");

    private final int serviceErrorCode;
    private final String serviceErrorMessage;
}
