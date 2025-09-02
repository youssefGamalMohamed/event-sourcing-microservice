package com.youssef.gamal.ecommerce.microservice.product.command.controllers;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.product.commands.ProductCommandDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.youssef.gamal.ecommerce.microservice.product.command.mappers.ProductMapper;
import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.services.ProductServiceIfc;


@RestController
@Slf4j
public class ProductController {

    private final ProductServiceIfc productService;
    private final ProductMapper productMapper;

    public ProductController(ProductServiceIfc productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductCommandDto product) {
        log.info("Creating product: {}", product);

        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped product DTO to entity: {}", productEntity);
        Product createdProduct = productService.createProduct(productEntity);
        log.info("Created product: {}", createdProduct);

        return createdProduct;
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable String id, @RequestBody ProductCommandDto product) {
        log.info("Updating product with id: {} with new data: {}", id, product);

        Product productEntity = productMapper.toEntity(product);
        log.info("Mapped product DTO to entity: {}", productEntity);
        productService.updateProduct(id, productEntity);
        log.info("Updated product with id: {}", id);

    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "id") String id) {
        log.info("Deleting product with id: {}", id);
        productService.deleteProduct(id);
        log.info("Deleted product with id: {}", id);
    }
}
