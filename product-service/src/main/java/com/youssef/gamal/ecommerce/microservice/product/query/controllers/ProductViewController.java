package com.youssef.gamal.ecommerce.microservice.product.query.controllers;

import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.InternalServerErrorResponse;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.NotFoundResponse;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import com.youssef.gamal.ecommerce.microservice.product.query.mappers.ProductViewMapper;
import com.youssef.gamal.ecommerce.microservice.product.query.services.ProductViewService;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.query.ProducQueryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Product View", description = "Operations to retrieve product information")
public class ProductViewController {


    private final ProductViewService productViewService;
    private final ProductViewMapper productViewMapper;

    public ProductViewController(ProductViewService productViewService, ProductViewMapper productViewMapper) {
        this.productViewService = productViewService;
        this.productViewMapper = productViewMapper;
    }


    @GetMapping("/products/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find all historical versions of a product by its original ID",
            description = "Retrieves a paged list of all historical versions of a product, ordered by modification date.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved historical data",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerErrorResponse.class)))
    public Page<ProducQueryResponse> findAllHistoryByOriginalId(@PathVariable String id, @ParameterObject Pageable pageable) {
        log.info("findById called with snapshotId: {},pageable:{}", id, pageable);
        Page<ProductView> productViewPage = productViewService.findAllByOriginalId(id, pageable);
        log.info("ProductView found with snapshotId: {}, TotalElement:{}", id, productViewPage.getTotalElements());
        return productViewPage.map(productViewMapper::toDto);
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find the latest version of a product by its original ID",
            description = "Retrieves the most recent version of a product based on its original ID. Throws 404 if the product is not found or has been deleted.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the latest product view",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProducQueryResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not Found - The product with the specified ID was not found or has been deleted",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFoundResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalServerErrorResponse.class)))
    public ProducQueryResponse findByOriginalIdAndWithLastHistory(@PathVariable String id) {
        log.info("findById called with snapshotId: {}", id);
        ProductView productView = productViewService.findByOriginalIdAndWithLastHistory(id);
        log.info("ProductView found with snapshotId: {}", id);
        return productViewMapper.toDto(productView);
    }
}
