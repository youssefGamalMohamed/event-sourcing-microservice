package com.youssef.gamal.ecommerce.microservice.common.response;

import com.youssef.gamal.ecommerce.microservice.common.exceptions.BadRequestException;
import com.youssef.gamal.ecommerce.microservice.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testSuccessFactoryMethod() {
        ApiResponse<String> response = ApiResponse.success("Hello World", 200, "/api/test");

        assertNotNull(response.getTimestamp());
        assertEquals(200, response.getStatus());
        assertEquals("Operation completed successfully", response.getMessage());
        assertEquals("Hello World", response.getData());
        assertEquals("/api/test", response.getPath());
    }

    @Test
    void testSuccessWithCustomMessageFactoryMethod() {
        ApiResponse<String> response = ApiResponse.success("Hello World", "Custom Success Message", 201, "/api/create");

        assertNotNull(response.getTimestamp());
        assertEquals(201, response.getStatus());
        assertEquals("Custom Success Message", response.getMessage());
        assertEquals("Hello World", response.getData());
        assertEquals("/api/create", response.getPath());
    }

    @Test
    void testErrorFactoryMethod() {
        ApiResponse<Void> response = ApiResponse.error(404, "Resource not found", "/api/categories/123");

        assertNotNull(response.getTimestamp());
        assertEquals(404, response.getStatus());
        assertEquals("Resource not found", response.getMessage());
        assertNull(response.getData());
        assertEquals("/api/categories/123", response.getPath());
    }

    @Test
    void testExceptionInstantiation() {
        ResourceNotFoundException notFound = new ResourceNotFoundException("Not found");
        assertEquals("Not found", notFound.getMessage());

        BadRequestException badRequest = new BadRequestException("Bad request");
        assertEquals("Bad request", badRequest.getMessage());
    }
}
