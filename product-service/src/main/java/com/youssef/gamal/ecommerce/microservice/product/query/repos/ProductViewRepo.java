package com.youssef.gamal.ecommerce.microservice.product.query.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.ecommerce.microservice.product.query.models.ProductView;

import java.util.Optional;

@Repository
public interface ProductViewRepo extends MongoRepository<ProductView, String> {

    Page<ProductView> findAllByOriginalId(String originalId, Pageable pageable);

    Optional<ProductView> findFirstByOriginalIdOrderByLastModifiedDateDesc(String originalId);
}
