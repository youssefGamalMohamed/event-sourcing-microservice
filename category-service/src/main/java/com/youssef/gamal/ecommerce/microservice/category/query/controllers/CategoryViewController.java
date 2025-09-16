package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.NotFoundResponse;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.services.CategoryViewServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Category Query", description = "Operations related to querying categories and their snapshots")
public class CategoryViewController {

    private final CategoryViewServiceIfc categoryViewService;
    private final CategoryViewMapper categoryViewMapper;

    @GetMapping("/categories/{originalId}/snapshots")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all snapshots of a category",
            description = "Returns a paginated list of all snapshots of a category by its original UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category snapshots retrieved successfully",
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
    public Page<CategoryQueryResponse> findAllSnapshotsByOriginalId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID originalId,
            @ParameterObject Pageable pageable) {

        log.info("findAllSnapshotsByOriginalId called with originalId: {}, pageable: {}", originalId, pageable);
        Page<CategoryView> categoryViewPage = categoryViewService.findAllByOriginalId(originalId.toString(), pageable);
        return categoryViewPage.map(categoryViewMapper::toDto);
    }

    @GetMapping("/categories/{originalId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get the latest snapshot of a category",
            description = "Returns the current/latest snapshot of a category including last modification history."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category snapshot retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryQueryResponse.class)
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
    public CategoryQueryResponse findLatestSnapshotByOriginalId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID originalId) {

        log.info("findLatestSnapshotByOriginalId called with originalId: {}", originalId);
        CategoryView categoryView = categoryViewService.findByOriginalIdAndWithLastSnapshot(originalId.toString());
        return categoryViewMapper.toDto(categoryView);
    }

    @GetMapping(value = "/categories", params = "snapshotId")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get snapshot by snapshotId",
            description = "Returns a specific snapshot of a category by its snapshot UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category snapshot retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryQueryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Snapshot not found",
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
    public CategoryQueryResponse findBySnapshotId(
            @Parameter(
                    description = "Snapshot UUID of the category",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @RequestParam UUID snapshotId) {

        log.info("findBySnapshotId called with snapshotId: {}", snapshotId);
        CategoryView categoryView = categoryViewService.findBySnapshotId(snapshotId.toString());
        return categoryViewMapper.toDto(categoryView);
    }

    @GetMapping("/categories/{originalId}/snapshots/{snapshotId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get snapshot by originalId and snapshotId",
            description = "Returns a specific snapshot of a category for a given originalId and snapshotId."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category snapshot retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryQueryResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Snapshot not found",
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
    public CategoryQueryResponse findByOriginalIdAndSnapshotId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    example = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID originalId,
            @Parameter(
                    description = "Snapshot UUID of the category",
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID snapshotId) {

        log.info("findByOriginalIdAndSnapshotId called with originalId: {}, snapshotId: {}", originalId, snapshotId);
        CategoryView categoryView = categoryViewService.findByOriginalIdAndSnapShotId(originalId.toString(), snapshotId.toString());
        return categoryViewMapper.toDto(categoryView);
    }
}
