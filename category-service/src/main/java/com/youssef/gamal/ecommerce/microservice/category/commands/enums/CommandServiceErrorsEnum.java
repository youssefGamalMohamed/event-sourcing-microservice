package com.youssef.gamal.ecommerce.microservice.category.commands.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommandServiceErrorsEnum {

    INTERNAL_SERVER_ERROR(1001, "An unexpected error occurred. Please contact support."),
    CATEGORY_NOT_FOUND(1002, "The requested category could not be found.");

    private final int serviceErrorCode;
    private final String serviceErrorMessage;
}
