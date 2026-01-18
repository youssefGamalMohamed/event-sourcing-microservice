package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.models.CreateCommand;
import com.youssef.gamal.ecommerce.microservice.category.commands.models.UpdateCommand;
import com.youssef.gamal.ecommerce.microservice.category.shared.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@Tag(name = "Category Commands", description = "Operations related to managing categories (create, update, delete)")
public interface RESTCommandControllerIfc {


    @PostMapping("/categories")
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category and returns the created category data."
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
                                    schema = @Schema(type = "string", format = "uri")
                            )
                    },
                    content = @Content(
                            schema = @Schema(implementation = CreateCommand.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                           
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category already exists",
                    content = @Content(schema = @Schema(implementation = ConflictErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Service Unavailable",
                    content = @Content(schema = @Schema(implementation = ServiceUnavailableResponse.class))
            )
    })
    ResponseEntity<CreateCommand> save(
            @RequestBody(
                    description = "Category data for creation",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateCommand.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody
            CreateCommand createCommand
    );


    @PutMapping("/categories/{id}")
    @Operation(
            summary = "Update a category",
            description = "Updates an existing category by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully",
                    content = @Content(schema = @Schema(implementation = UpdateCommand.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(schema = @Schema(implementation = NotFoundResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category already exists",
                    content = @Content(schema = @Schema(implementation = ConflictErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Service Unavailable",
                    content = @Content(schema = @Schema(implementation = ServiceUnavailableResponse.class))
            )
    })
    ResponseEntity<UpdateCommand> update(
            @Parameter(description = "Category ID", required = true)
            @PathVariable String id,

            @RequestBody(
                    description = "Category data for update",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateCommand.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody
            UpdateCommand updateCommand
    );


    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(schema = @Schema(implementation = NotFoundResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Service Unavailable",
                    content = @Content(schema = @Schema(implementation = ServiceUnavailableResponse.class))
            )
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Category ID", required = true)
            @PathVariable String id
    );

}
