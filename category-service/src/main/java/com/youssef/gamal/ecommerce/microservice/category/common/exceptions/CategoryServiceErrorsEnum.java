package com.youssef.gamal.ecommerce.microservice.category.common.exceptions;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CategoryServiceErrorsEnum {

    INTERNAL_SERVER_ERROR(1001, "An unexpected error occurred. Please contact support."),
    CATEGORY_NOT_FOUND(1002, "The requested category could not be found."),
    CATEGORY_NAME_ALREADY_EXISTS(1003, "A category with this name already exists."),
    CATEGORY_REQUEST_BODY_FAILED_VALIDATION(1005, "The provided category data is invalid.");

    private final int serviceErrorCode;
    private final String serviceErrorMessage;
}
