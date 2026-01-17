package com.youssef.gamal.ecommerce.microservice.category.shared.exceptions;


import lombok.Getter;

import java.io.Serial;

@Getter
public class AlreadyExistException extends RuntimeException {


	@Serial
    private static final long serialVersionUID = 2237216282015892410L;

	public AlreadyExistException(String categoryName) {
        super("Category Already Exists with Name = " + categoryName);
    }
}
