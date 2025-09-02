package com.youssef.gamal.ecommerce.microservice.product.command.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
}
