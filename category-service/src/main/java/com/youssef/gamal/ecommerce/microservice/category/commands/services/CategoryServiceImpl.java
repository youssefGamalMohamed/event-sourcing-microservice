package com.youssef.gamal.ecommerce.microservice.category.commands.services;

import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.repos.CategoryRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryServiceIfc {

	private final CategoryRepo categoryRepo;
	private final CategoryMapper categoryMapper;
	private final CategoryEventProducerIfc categoryEventProducerIfc;
	
	@Override
	public Category save(Category category) {
		log.info("CategoryServiceImpl -> save({})",category);
		Category savedCategory =  categoryRepo.save(category);
		log.info("Category Saved Successfully: newCategory={}", savedCategory);
		
		// publish event to kafka
		categoryEventProducerIfc.publish(categoryMapper.toEvent(savedCategory, CategoryEventType.CREATED.toString()));
		return savedCategory;
	}

	@Override
	public Category update(String id, Category updatedCategory) {
	    log.info("CategoryServiceImpl -> update(id={}, updatedCategory={})", id, updatedCategory);

	    Category existingCategory = categoryRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

	    // update category
	    categoryMapper.updateFrom(updatedCategory, existingCategory);
	    
	    Category savedCategory = categoryRepo.save(existingCategory);
	    log.info("Category Updated Successfully: {}", savedCategory);
	    
	    // publish event to kafka
	    categoryEventProducerIfc.publish(categoryMapper.toEvent(savedCategory, CategoryEventType.UPDATED.toString()));
	    return savedCategory;
	}

}
