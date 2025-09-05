package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.ConflictErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.NotFoundResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.ValidationErrorResponse;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommandDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Category Management", description = "Operations related to managing categories")
public class CategoryController {

    private final CategoryServiceIfc categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/categories")
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category and returns the created category data with Location header pointing to the new resource."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    headers = {
                            @Header(
                                    name = "Location",
                                    description = "URI of the newly created category resource",
                                    required = true,
                                    schema = @Schema(
                                            type = "string",
                                            format = "uri",
                                            example = "/categories/a1b2c3d4-e5f6-7890-1234-567890abcdef"
                                    )
                            )
                    },
                    content = @Content(
                            schema = @Schema(implementation = CategoryCommandDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or validation errors",
                    content = @Content(
                            schema = @Schema(implementation = ValidationErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category name already exists",
                    content = @Content(
                            schema = @Schema(implementation = ConflictErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = InternalServerErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })
    public ResponseEntity<CategoryCommandDto> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category data to create. Only 'name' field is required.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CategoryCommandDto.class)
                    )
            )
            @Valid @RequestBody CategoryCommandDto categoryCommandDto) {

        log.info("CategoryController -> save() called with categoryDto={}", categoryCommandDto);

        Category category = categoryMapper.toEntity(categoryCommandDto);
        Category savedCategory = categoryService.save(category);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        // Build URI for Location header
        URI location = URI.create("/categories/" + savedCategory.getId());

        log.info("CategoryController -> save() completed successfully with result={}, location={}",
                savedDto, location);

        return ResponseEntity
                .created(location)
                .body(savedDto);
    }

    @PutMapping("/categories/{id}")
    @Operation(
            summary = "Update an existing category",
            description = "Updates an existing category by its ID and returns the updated category data."
    )
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
                    responseCode = "400",
                    description = "Invalid request body or validation errors",
                    content = @Content(
                            schema = @Schema(implementation = ValidationErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            schema = @Schema(implementation = NotFoundResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category name already exists",
                    content = @Content(
                            schema = @Schema(implementation = ConflictErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = InternalServerErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })


    public ResponseEntity<CategoryCommandDto> update(
            @Parameter(
                    description = "Unique identifier of the category to update",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable(name = "id") String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated category data. Only 'name' field should be provided for update.",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CategoryCommandDto.class)
                    )
            )
            @Valid @RequestBody CategoryCommandDto categoryDto) {
        log.info("CategoryController -> update() called with id={}, categoryDto={}", id, categoryDto);

        Category updatedCategory = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.update(id, updatedCategory);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> update() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }


    @DeleteMapping("/categories/{id}")
    @Operation(
            summary = "Delete a category",
            description = "Permanently deletes a category by its ID. This action cannot be undone."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted successfully. No content returned.",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            schema = @Schema(implementation = NotFoundResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = InternalServerErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "Unique identifier of the category to delete",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable(name = "id") String id) {
        log.info("CategoryController -> delete() called with id={}", id);

        categoryService.delete(id);

        log.info("CategoryController -> delete() completed successfully for id={}", id);
        return ResponseEntity.noContent().build();
    }

}