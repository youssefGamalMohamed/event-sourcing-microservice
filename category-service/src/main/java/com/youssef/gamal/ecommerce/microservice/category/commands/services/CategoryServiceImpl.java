package com.youssef.gamal.ecommerce.microservice.category.commands.services;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.repos.CategoryRepo;
import com.youssef.gamal.ecommerce.microservice.category.common.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.common.exceptions.AlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryServiceIfc {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final CategoryEventProducerIfc categoryEventProducerIfc;

    @Override
    @Transactional(Transactional.TxType.REQUIRED) // default
    public Category save(Category category) {
        log.info("CategoryServiceImpl -> save({})", category);

        categoryRepo.findByName(category.getName())
                .ifPresent(existing -> {
                    log.warn("CategoryServiceImpl -> save(name: {}, existingId: {}) already exists",
                            existing.getName(), existing.getId());
                    throw new AlreadyExistException(existing.getName());
                });

        Category savedCategory = categoryRepo.save(category);
        log.info("Category Saved Successfully: newCategory={}", savedCategory);

        // publish event to kafka
        categoryEventProducerIfc.publish(categoryMapper.toEvent(savedCategory, CategoryEventType.CREATED.toString()));
        return savedCategory;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED) // default
    public Category update(String id, Category updatedCategory) {
        log.info("CategoryServiceImpl -> update(id={}, updatedCategory={})", id, updatedCategory);

        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));

        // ensure new name is unique
        categoryRepo.findByName(updatedCategory.getName())
                .filter(found -> !found.getId().equals(id))
                .ifPresent(found -> {
                    log.warn("CategoryServiceImpl -> update(name: {}, id: {}) already exists in another category",
                            found.getName(), found.getId());
                    throw new AlreadyExistException(updatedCategory.getName());
                });

        // update category fields
        categoryMapper.updateFrom(updatedCategory, existingCategory);

        Category savedCategory = categoryRepo.save(existingCategory);
        log.info("Category Updated Successfully: {}", savedCategory);

        // publish event to kafka
        categoryEventProducerIfc.publish(categoryMapper.toEvent(savedCategory, CategoryEventType.UPDATED.toString()));
        return savedCategory;
    }


    @Override
    @Transactional(Transactional.TxType.REQUIRED) // default
    public void delete(String id) {
        log.info("CategoryServiceImpl -> delete({})", id);

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));

        // delete by reference
        categoryRepo.delete(category);

        // publish event to kafka
        categoryEventProducerIfc.publish(categoryMapper.toEvent(category, CategoryEventType.DELETED.toString()));
    }
}
