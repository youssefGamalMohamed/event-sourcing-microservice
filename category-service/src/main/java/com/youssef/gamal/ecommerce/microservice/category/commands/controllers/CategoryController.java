package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommandDto;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.exceptions.models.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Category Management", description = "Operations related to managing categories")
public class CategoryController {

    private final CategoryServiceIfc categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/categories")
    @Operation(summary = "Create a new category", description = "Creates a new category and returns the created category data.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    headers = {
                            @Header(
                                    name = "location",
                                    description = "location of created Category",
                                    schema = @Schema(implementation = URI.class),
                                    examples = {
                                            @ExampleObject(value = "/catego")
                                    }
                            )
                    },
                    content = @Content(
                            schema = @Schema(implementation = CategoryCommandDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or data",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })
    public ResponseEntity<CategoryCommandDto> save(@RequestBody CategoryCommandDto categoryCommandDto) {
        log.info("CategoryController -> save() called with categoryDto={}", categoryCommandDto);

        Category category = categoryMapper.toEntity(categoryCommandDto);
        Category savedCategory = categoryService.save(category);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> save() completed successfully with result={}", savedDto);
        return ResponseEntity.created(URI.create("/categories/" + savedCategory.getId()))
                .body(savedDto);
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "Update an existing category", description = "Updates an existing category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = CategoryCommandDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })
    public ResponseEntity<CategoryCommandDto> update(
            @Parameter(description = "ID of the category to update") @PathVariable(name = "id") String id,
            @RequestBody CategoryCommandDto categoryDto) {
        log.info("CategoryController -> update() called with id={}, categoryDto={}", id, categoryDto);

        Category updatedCategory = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.update(id, updatedCategory);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> update() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Delete a category", description = "Deletes a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the category to delete") @PathVariable(name = "id") String id) {
        log.info("CategoryController -> delete() called with id={}", id);

        categoryService.delete(id);

        log.info("CategoryController -> delete() completed successfully for id={}", id);
        return ResponseEntity.noContent().build();
    }
}
