package com.youssef.gamal.ecommerce.microservice.product.command.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.events.producers.ProductEventProducerIfc;
import com.youssef.gamal.ecommerce.microservice.product.command.mappers.ProductMapper;
import com.youssef.gamal.ecommerce.microservice.product.command.repos.ProductRepo;
import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.common.exceptions.AlreadyExistException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServiceIfc {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final ProductEventProducerIfc productEventProducer;


    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);

        // Check for existing product with the same name before saving.
        Optional<Product> existingProduct = productRepo.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new AlreadyExistException(product.getName());
        }

        Product newProduct = productRepo.save(product);
        log.info("Product created with snapshotId = {}", newProduct.getId());

        log.info("Will Publish Product Created Event");
        productEventProducer.publish(productMapper.toEvent(newProduct, ProductEventType.CREATED.toString()));

        return newProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(String id, Product product) {
        log.info("Updating product with snapshotId: {} with new data: {}", id, product);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with snapshotId: " + id));

        // Check if the new name is different from the existing name.
        if (!existingProduct.getName().equals(product.getName())) {
            // Check for a product with the new name.
            Optional<Product> productWithNewName = productRepo.findByName(product.getName());
            // If a product with the new name exists and it's not the current product being updated, throw an exception.
            if (productWithNewName.isPresent() && !productWithNewName.get().getId().equals(id)) {
                throw new AlreadyExistException(product.getName());
            }
        }

        productMapper.updateFrom(product, existingProduct);
        Product updatedProduct = productRepo.save(existingProduct);
        log.info("Product updated with snapshotId = {}", updatedProduct.getId());

        log.info("Will Publish Product Updated Event");
        productEventProducer.publish(productMapper.toEvent(updatedProduct, ProductEventType.UPDATED.toString()));

        return updatedProduct;
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        log.info("Deleting product with snapshotId: {}", id);

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with snapshotId: " + id));

        productRepo.deleteById(id);
        log.info("Product deleted with snapshotId = {}", id);

        log.info("Will Publish Product Deleted Event");
        productEventProducer.publish(productMapper.toEvent(existingProduct, ProductEventType.DELETED.toString()));
    }

    @Override
    @Transactional
    public List<Product> removeCategoryFromAllProductsBy(String deleteCategoryId) {
//        log.info("removeCategoryFromAllProductsBy({})", deleteCategoryId);
//
//        // Get all products that currently reference this category
//        List<Product> affectedProducts = productRepo.findAllByCategoryId(deleteCategoryId);
//        log.info("Found {} products referencing categoryId={}", affectedProducts.size(), deleteCategoryId);    
//        
//        affectedProducts.forEach(p -> p.getCategoryIds().remove(deleteCategoryId));
//        productRepo.saveAll(affectedProducts);
//        
//        log.info("Successfully removed category with snapshotId = {} from list of the products" , deleteCategoryId);
//        
//        return affectedProducts;
    	
    	
    	return new ArrayList<>();
    }
	

}