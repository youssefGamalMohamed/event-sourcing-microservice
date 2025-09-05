package com.youssef.gamal.ecommerce.microservice.product.command.repos;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

}
