package com.youssef.gamal.ecommerce.microservice.product.common.enums;

import java.util.Optional;

public enum ProductEventType {

    CREATED, UPDATED, DELETED;


    /**
     * Converts a string value to a ProductEventType enum constant.
     * This method is case-insensitive and returns an Optional to handle cases where the
     * string does not match any enum constant.
     *
     * @param value the string to convert (e.g., "CREATED", "updated").
     * @return an Optional containing the corresponding enum constant, or an empty Optional if no match is found.
     */
    public static Optional<ProductEventType> fromValue(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(ProductEventType.valueOf(value.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}
