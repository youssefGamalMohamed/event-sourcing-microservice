package com.youssef.gamal.ecommerce.microservice.product.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommandDto;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryIntegrationServiceIfc {

    // ---------- COMMAND ----------
    CategoryCommandDto saveCategory(CategoryCommandDto categoryDto);

    CategoryCommandDto updateCategory(String id, CategoryCommandDto categoryDto);

    void deleteCategory(String id);



    // ---------- QUERY ----------
    CategoryViewDto findCategoryById(String id);

    Page<CategoryViewDto> findCategoryHistoryById(String id, Pageable pageable);

}
