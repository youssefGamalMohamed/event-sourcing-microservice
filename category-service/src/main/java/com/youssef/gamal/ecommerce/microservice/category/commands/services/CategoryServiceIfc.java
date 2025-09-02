package com.youssef.gamal.ecommerce.microservice.category.commands.services;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;

public interface CategoryServiceIfc {
	
	Category save(Category category);
	Category update(String id, Category updatedCategory);
    void delete(String id);
}
