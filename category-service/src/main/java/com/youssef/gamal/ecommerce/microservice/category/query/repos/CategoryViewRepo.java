package com.youssef.gamal.ecommerce.microservice.category.query.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.ecommerce.microservice.category.query.models.CategoryView;

import java.util.Optional;

@Repository
public interface CategoryViewRepo extends MongoRepository<CategoryView, String> {

    Page<CategoryView> findAllByOriginalId(String originalId, Pageable pageable);

    Optional<CategoryView> findFirstByOriginalIdOrderByLastModifiedDateDesc(String originalId);
}
