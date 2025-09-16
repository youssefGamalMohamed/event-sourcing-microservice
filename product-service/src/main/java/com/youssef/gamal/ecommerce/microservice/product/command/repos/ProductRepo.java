package com.youssef.gamal.ecommerce.microservice.product.command.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

//    /**
//     * Removes the given categoryId from all products that reference it.
//     */
//    @Modifying
//    @Transactional
//    @Query(value = """
//            DELETE FROM product_category_ids pci
//            WHERE pci.category_id = :categoryId
//            """, nativeQuery = true)
//    void removeCategoryFromAllProducts(String categoryId);

//    
//    @Query("SELECT p FROM Product p WHERE :categoryId MEMBER OF p.categoryIds")
//    List<Product> findAllByCategoryId(String categoryId);
}