package com.youssef.gamal.ecommerce.microservice.category.query.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.youssef.gamal.ecommerce.microservice.category.query.models.CategoryView;


public interface CategoryViewServiceIfc {
	
	CategoryView addCategorytView(CategoryView categoryView);

    Page<CategoryView> findAllByOriginalId(String id, Pageable pageable);

    CategoryView findByOriginalIdAndWithLastHistory(String originalId);
}
