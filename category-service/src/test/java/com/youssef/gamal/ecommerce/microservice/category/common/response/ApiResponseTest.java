package com.youssef.gamal.ecommerce.microservice.category.common.response;

import org.junit.jupiter.api.Test;
import java.time.Instant;
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
    void testErrorFactoryMethod() {
        ApiResponse<Void> response = ApiResponse.error(404, "Resource not found", "/api/categories/123");

        assertNotNull(response.getTimestamp());
        assertEquals(404, response.getStatus());
        assertEquals("Resource not found", response.getMessage());
        assertNull(response.getData());
        assertEquals("/api/categories/123", response.getPath());
    }
}
