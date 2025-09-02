package com.youssef.gamal.ecommerce.microservice.category.common.enums;


import java.util.Optional;

public enum CategoryEventType {
    CREATED,
    UPDATED,
    DELETED;

    /**
     * Converts a string value to a CategoryEventType enum constant.
     * This method is case-insensitive and returns an Optional to handle cases where the
     * string does not match any enum constant.
     *
     * @param value the string to convert (e.g., "CREATED", "updated").
     * @return an Optional containing the corresponding enum constant, or an empty Optional if no match is found.
     */
    public static Optional<CategoryEventType> fromValue(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(CategoryEventType.valueOf(value.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            // Log the error if necessary, or simply return an empty Optional.
            return Optional.empty();
        }
    }
}
