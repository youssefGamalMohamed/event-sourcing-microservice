package com.youssef.gamal.ecommerce.microservice.product.command.services;

import com.youssef.gamal.ecommerce.microservice.product.command.models.Product;

public interface ProductServiceIfc {

    Product createProduct(Product product);

    Product updateProduct(String id, Product product);
}
