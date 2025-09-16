package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryQueryIntegrationServiceIfc {
    // ---------- QUERY ----------
    CategoryQueryResponse findCategoryById(String id);

    Page<CategoryQueryResponse> findCategoryHistoryById(String id, Pageable pageable);

    Set<CategoryQueryResponse> findAllByIds(Set<String> categoryIds);
}
