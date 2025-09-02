package com.youssef.gamal.ecommerce.microservice.product.command.services;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.command.mappers.ProductMapper;
import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.repos.ProductRepo;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProductServiceImpl implements ProductServiceIfc {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    
    @Qualifier("rabbitMQProductEventProducerImpl")
    private final ProductEventProducerIfc productEventProducer;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper, ProductEventProducerIfc productEventProducer) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.productEventProducer = productEventProducer;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);

        Product newProduct = productRepo.save(product);

        log.info("Product created with id = {}", newProduct.getId());

        log.info("Will Publish Product Created Event");

        // publish product created event
        productEventProducer.publish(productMapper.toEvent(newProduct, ProductEventType.CREATED.toString()));

        return newProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(String id, Product product) {
        log.info("Updating product with id: {} with new data: {}", id, product);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        productMapper.updateFrom(product, existingProduct);
        Product updatedProduct = productRepo.save(existingProduct);
        log.info("Product updated with id = {}", updatedProduct.getId());

        // publish product created event
        productEventProducer.publish(productMapper.toEvent(updatedProduct, ProductEventType.UPDATED.toString()));

        return updatedProduct;
    }


}
