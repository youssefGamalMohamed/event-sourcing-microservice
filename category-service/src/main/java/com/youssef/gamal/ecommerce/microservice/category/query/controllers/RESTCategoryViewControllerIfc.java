package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import com.youssef.gamal.ecommerce.microservice.category.shared.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.category.shared.exceptions.NotFoundResponse;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Category Query", description = "Operations related to querying categories and their snapshots")
public interface RESTCategoryViewControllerIfc {


    @Operation(
            summary = "Get all snapshots of a category",
            description = "Returns a paginated list of all snapshots of a category by its original UUID."
    )
    @ApiResponses({
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )
            )
    })
    Page<CategoryQueryResponse> findAllSnapshotsByOriginalId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID originalId,
            @ParameterObject Pageable pageable
    );


    @Operation(
            summary = "Get the latest snapshot of a category",
            description = "Returns the current/latest snapshot of a category."
    )
    @ApiResponses({
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )
            )
    })
    CategoryQueryResponse findLatestSnapshotByOriginalId(
            @Parameter(
                    description = "Original UUID of the category",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID originalId
    );


    @Operation(
            summary = "Get snapshot by snapshotId",
            description = "Returns a specific snapshot of a category by its snapshot UUID."
    )
    @ApiResponses({
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )
            )
    })
    CategoryQueryResponse findBySnapshotId(
            @Parameter(
                    description = "Snapshot UUID of the category",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @RequestParam UUID snapshotId
    );


    @Operation(
            summary = "Get snapshot by originalId and snapshotId",
            description = "Returns a specific snapshot of a category for a given originalId and snapshotId."
    )
    @ApiResponses({
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class)
                    )
            )
    })
    CategoryQueryResponse findByOriginalIdAndSnapshotId(
            @PathVariable UUID originalId,
            @PathVariable UUID snapshotId
    );
}
