package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryQueryIntegrationServiceIfc {
    // ---------- QUERY ----------
    CategoryViewDto findCategoryById(String id);

    Page<CategoryViewDto> findCategoryHistoryById(String id, Pageable pageable);

}
