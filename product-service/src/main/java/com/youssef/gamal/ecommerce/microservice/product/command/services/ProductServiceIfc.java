package com.youssef.gamal.ecommerce.microservice.product.command.services;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;

public interface ProductServiceIfc {

    Product createProduct(Product product);

    Product updateProduct(String id, Product product);

    void deleteProduct(String id);
}
