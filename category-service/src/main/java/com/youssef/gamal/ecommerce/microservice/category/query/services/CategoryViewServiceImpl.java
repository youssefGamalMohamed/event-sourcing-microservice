package com.youssef.gamal.ecommerce.microservice.category.query.services;

import java.util.NoSuchElementException;

import com.youssef.gamal.ecommerce.microservice.category.commands.enums.CategoryEventType;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.query.models.CategoryView;
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
    @Caching(
            put = {
                    @CachePut(
                        key = "#result.originalId",
                        condition = "#result.eventType == 'CREATED' || #result.eventType == 'UPDATED'"
                    )
            },
            evict = {
                    @CacheEvict(key = "#result.originalId", condition = "#result.eventType == 'DELETED'")
            }
    )
    public CategoryView addCategorytView(CategoryView categoryView) {
        log.info("addCategorytView called with categoryView: {}", categoryView);
        CategoryView savedCategoryView = categoryRepo.save(categoryView);
        log.info("CategoryView saved with id: {}", savedCategoryView.getId());
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
    @Cacheable(key = "#originalId")
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
