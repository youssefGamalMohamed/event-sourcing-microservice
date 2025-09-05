package com.youssef.gamal.ecommerce.microservice.category.query.repos;

import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryViewRepo extends MongoRepository<CategoryView, String> {

    Page<CategoryView> findAllByOriginalId(String originalId, Pageable pageable);

    Optional<CategoryView> findFirstByOriginalIdOrderByLastModifiedDateDesc(String originalId);
}
