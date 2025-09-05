package com.youssef.gamal.ecommerce.microservice.category.common.exceptions;


import lombok.Getter;

@Getter
public class CategoryAlreadyExistException extends RuntimeException {

    public CategoryAlreadyExistException(String categoryName) {
        super("Category Already Exists with Name = " + categoryName);
    }
}
