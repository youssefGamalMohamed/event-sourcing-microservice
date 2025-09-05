package com.youssef.gamal.ecommerce.microservice.product.command.controllers;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.mappers.ProductMapper;
import com.youssef.gamal.ecommerce.microservice.product.command.services.ProductServiceIfc;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.ConflictErrorResponse;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.NotFoundResponse;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.ValidationErrorResponse;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands.ProductCommandDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product", description = "Operations related to managing products")
public class ProductController {

    private final ProductServiceIfc productService;
    private final ProductMapper productMapper;

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product",
            description = "Creates a new product with the provided details and saves it to the database.")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCommandDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request - The request body failed validation",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflict - A product with the same name already exists",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerErrorResponse.class)))
    public ProductCommandDto createProduct(@RequestBody ProductCommandDto product) {
        log.info("Creating product: {}", product);
        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped product Request DTO to entity: {}", productEntity);
        Product createdProduct = productService.createProduct(productEntity);
        log.info("Created product: {}", createdProduct);
        ProductCommandDto responseDto = productMapper.toDto(createdProduct);
        log.info("Mapped created product to Response DTO: {}", responseDto);
        return responseDto;
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing product",
            description = "Updates an existing product with the specified ID and new details.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCommandDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request - The request body failed validation",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not Found - The product with the specified ID was not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundResponse.class)))
    @ApiResponse(responseCode = "409", description = "Conflict - A product with the new name already exists",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConflictErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerErrorResponse.class)))
    public ProductCommandDto updateProduct(@PathVariable String id, @RequestBody ProductCommandDto product) {
        log.info("Updating product with id: {} with new data: {}", id, product);
        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped updated product Request DTO to entity: {}", productEntity);
        Product updatedProduct = productService.updateProduct(id, productEntity);
        log.info("Updated product with id: {}", id);
        ProductCommandDto responseDto = productMapper.toDto(updatedProduct);
        log.info("Mapped product entity to DTO response : {}", responseDto);
        return responseDto;
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product",
            description = "Deletes a product with the specified ID from the database.")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully - No Content")
    @ApiResponse(responseCode = "404", description = "Not Found - The product with the specified ID was not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerErrorResponse.class)))
    public void deleteProduct(@PathVariable(name = "id") String id) {
        log.info("Deleting product with id: {}", id);
        productService.deleteProduct(id);
        log.info("Deleted product with id: {}", id);
    }
}
