package com.youssef.gamal.ecommerce.microservice.category.common.exceptions;


import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String categoryName) {
        super("Category Already Exists with Name = " + categoryName);
    }
}
