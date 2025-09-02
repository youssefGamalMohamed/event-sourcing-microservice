package com.youssef.gamal.ecommerce.microservice.product.integrations.interfaces;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;

public interface CategoryIntegrationServiceIfc {

    CategoryViewDto findCategoryById(String id);

}
