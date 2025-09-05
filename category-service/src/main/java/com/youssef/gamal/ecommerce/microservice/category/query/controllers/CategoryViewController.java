package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.ErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.NotFoundResponse;
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
                            schema = @Schema(implementation = Page.class)
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
                            schema = @Schema(implementation = CategoryViewDto.class)
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
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = InternalServerErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
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
