package com.youssef.gamal.ecommerce.microservice.product.command.controllers;

import com.youssef.gamal.ecommerce.microservice.common.response.ApiResponse;
import com.youssef.gamal.ecommerce.microservice.product.command.models.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.services.ProductServiceIfc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerResponseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceIfc productService;

    @Test
    void testProductCreationResponseWrapped() throws Exception {
        Product createdProduct = Product.builder()
                .id("prod-100")
                .name("Laptop")
                .description("High end laptop")
                .price(1200.00)
                .build();

        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        String payload = """
                {
                    "name": "Laptop",
                    "description": "High end laptop",
                    "price": 1200.00,
                    "quantity": 5
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Operation completed successfully"))
                .andExpect(jsonPath("$.data.name").value("Laptop"))
                .andExpect(jsonPath("$.path").value("/products"));
    }

    @Test
    void testUnhandledExceptionReturnsApiResponseWithErrorStatus() throws Exception {
        when(productService.createProduct(any(Product.class))).thenThrow(new RuntimeException("Product creation failed"));

        String payload = """
                {
                    "name": "Laptop",
                    "description": "High end laptop",
                    "price": 1200.00,
                    "quantity": 5
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Product creation failed"))
                .andExpect(jsonPath("$.path").value("/products"));
    }
}
