package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;
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
class CategoryControllerResponseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceIfc categoryService;

    @Test
    void testCategoryResponseWrappedInApiResponse() throws Exception {
        Category savedCategory = new Category();
        savedCategory.setId("cat-100");
        savedCategory.setName("Electronics");

        when(categoryService.save(any(Category.class))).thenReturn(savedCategory);

        String jsonPayload = """
                {
                    "id": "cat-100",
                    "name": "Electronics"
                }
                """;

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Operation completed successfully"))
                .andExpect(jsonPath("$.data.id").value("cat-100"))
                .andExpect(jsonPath("$.path").value("/categories"));
    }

    @Test
    void testUnhandledExceptionReturnsApiResponseWithErrorStatus() throws Exception {
        when(categoryService.save(any(Category.class))).thenThrow(new RuntimeException("Database error"));

        String jsonPayload = """
                {
                    "id": "cat-100",
                    "name": "Electronics"
                }
                """;

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Database error"))
                .andExpect(jsonPath("$.path").value("/categories"));
    }
}

