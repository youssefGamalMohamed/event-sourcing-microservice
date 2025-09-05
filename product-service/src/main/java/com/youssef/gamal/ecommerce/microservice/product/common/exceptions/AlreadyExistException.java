package com.youssef.gamal.ecommerce.microservice.product.common.exceptions;


import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String productName) {
        super("Product Already Exists with Name = " + productName);
    }
}
