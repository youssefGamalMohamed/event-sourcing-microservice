package com.youssef.gamal.ecommerce.microservice.category.query.services;

import java.util.NoSuchElementException;

import com.youssef.gamal.ecommerce.microservice.category.common.enums.CategoryEventType;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.repos.CategoryViewRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "categoryViewCache") // ✅ single source of truth for cache name
public class CategoryViewServiceImpl implements CategoryViewServiceIfc {

    private final CategoryViewRepo categoryRepo;

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(
                            key = "#result.originalId",
                            condition = "#eventType.toString() == 'CREATED' || #eventType.toString() == 'UPDATED'"
                    )
            },
            evict = {
                    @CacheEvict(key = "#categoryView.originalId", condition = "#eventType.toString() == 'DELETED'")
            }
    )
    public CategoryView saveCategoryView(CategoryView categoryView, CategoryEventType eventType) {
        log.info("saveCategoryView called with categoryView: {} , eventType: {}", categoryView, eventType);

        categoryView.setEventType(eventType.toString()); // ✅ ensure DB consistency
        CategoryView savedCategoryView = categoryRepo.save(categoryView);

        log.info("[CATEGORY_VIEW:SAVE] id={}, originalId={}, eventType={}", savedCategoryView.getId(), savedCategoryView.getOriginalId(), eventType);

        return savedCategoryView;
    }

    @Override
    public Page<CategoryView> findAllByOriginalId(String originalId, Pageable pageable) {
        log.info("findAllByOriginalId called with originalId: {}, pageable: {}", originalId, pageable);
        Page<CategoryView> categoryViews = categoryRepo.findAllByOriginalId(originalId, pageable);
        log.info("CategoryViews found with originalId: {}, count: {}", originalId, categoryViews.getTotalElements());
        return categoryViews;
    }

    @Override
    @Cacheable(key = "#originalId", unless = "#result == null") // cache element when element != null
    public CategoryView findByOriginalIdAndWithLastHistory(String originalId) {
        log.info("findByOriginalIdAndWithLastHistory called with originalId: {}", originalId);

        CategoryView categoryView = categoryRepo.findFirstByOriginalIdOrderByLastModifiedDateDesc(originalId)
                .orElseThrow(() -> new NoSuchElementException("Category view not found with originalId: " + originalId));

        // ✅ Return 404 if the last event was DELETE
        if (CategoryEventType.DELETED.toString().equalsIgnoreCase(categoryView.getEventType())) {
            log.warn("Category with originalId {} was deleted. Throwing NoSuchElementException", originalId);
            throw new NoSuchElementException("Category view not found with originalId: " + originalId);
        }

        log.info("CategoryView found with originalId: {}, id: {}", originalId, categoryView.getId());
        return categoryView;
    }
}
