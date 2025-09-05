package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.ErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.services.CategoryViewServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Category Query", description = "Operations related to querying categories and their history")
public class CategoryViewController {

    private final CategoryViewServiceIfc categoryViewService;
    private final CategoryViewMapper categoryViewMapper;

    @GetMapping("/categories/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all historical versions of a category",
            description = "Returns a paginated list of all historical versions of a category by its original UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category history retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryViewDto.class),
                            examples = @ExampleObject(
                                    name = "Category History Response",
                                    value = """
                                            {
                                              "content": [
                                                {
                                                  "id": "123e4567-e89b-12d3-a456-426614174000",
                                                  "originalId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                                                  "name": "Electronics",
                                                  "creationDate": "2025-09-05T10:30:00",
                                                  "createdBy": "admin",
                                                  "lastModifiedDate": "2025-09-05T11:00:00",
                                                  "lastModifiedBy": "admin",
                                                  "eventType": "CREATED"
                                                }
                                              ],
                                              "totalElements": 1,
                                              "totalPages": 1,
                                              "size": 20,
                                              "number": 0
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Internal Server Error",
                                            summary = "Example internal server error response",
                                            value = """
                                                    {
                                                        "timestamp": "2025-09-05T11:45:00",
                                                        "status": 500,
                                                        "message": "An unexpected error occurred. Please contact support.",
                                                        "serviceCode": "1001",
                                                        "path": "/categories/a1b2c3d4-e5f6-7890-1234-567890abcdef"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    public Page<CategoryViewDto> findAllHistoryByOriginalId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable("id") UUID id,
            @ParameterObject Pageable pageable) {

        log.info("findAllHistoryByOriginalId called with id: {}, pageable: {}", id, pageable);
        Page<CategoryView> categoryViewPage = categoryViewService.findAllByOriginalId(id.toString(), pageable);
        return categoryViewPage.map(categoryViewMapper::toDto);
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get the latest version of a category",
            description = "Returns the current/latest version of a category including last modification history."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryViewDto.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "id": "123e4567-e89b-12d3-a456-426614174000",
                                              "originalId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                                              "name": "Electronics",
                                              "creationDate": "2025-09-05T10:30:00",
                                              "createdBy": "admin",
                                              "lastModifiedDate": "2025-09-05T11:00:00",
                                              "lastModifiedBy": "admin",
                                              "eventType": "UPDATED"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Category Not Found",
                                            summary = "Example category not found response",
                                            value = """
                                                    {
                                                        "timestamp": "2025-09-05T11:45:00",
                                                        "status": 404,
                                                        "message": "The requested category could not be found.",
                                                        "serviceCode": "1002",
                                                        "path": "/categories/123e4567-e89b-12d3-a456-426614174000"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Internal Server Error",
                                            summary = "Example internal server error response",
                                            value = """
                                                    {
                                                        "timestamp": "2025-09-05T11:45:00",
                                                        "status": 500,
                                                        "message": "An unexpected error occurred. Please contact support.",
                                                        "serviceCode": "1001",
                                                        "path": "/categories/a1b2c3d4-e5f6-7890-1234-567890abcdef"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    public CategoryViewDto findByOriginalIdAndWithLastHistory(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable("id") UUID id) {

        log.info("findByOriginalIdAndWithLastHistory called with id: {}", id);
        CategoryView categoryView = categoryViewService.findByOriginalIdAndWithLastHistory(id.toString());
        return categoryViewMapper.toDto(categoryView);
    }
}
