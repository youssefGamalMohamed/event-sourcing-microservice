package com.youssef.gamal.ecommerce.microservice.product.query.repos;

import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductViewRepo extends MongoRepository<ProductView, String> {

    Page<ProductView> findAllByOriginalId(String originalId, Pageable pageable);

    Optional<ProductView> findFirstByOriginalIdOrderByLastModifiedDateDesc(String originalId);
}
