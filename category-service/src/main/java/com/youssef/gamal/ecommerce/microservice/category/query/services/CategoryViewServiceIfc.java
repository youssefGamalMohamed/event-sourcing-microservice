package com.youssef.gamal.ecommerce.microservice.category.query.services;

import com.youssef.gamal.ecommerce.microservice.category.common.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryViewServiceIfc {

    CategoryView saveCategoryView(CategoryView categoryView, CategoryEventType eventType);

    Page<CategoryView> findAllByOriginalId(String id, Pageable pageable);

    CategoryView findByOriginalIdAndWithLastHistory(String originalId);
}
