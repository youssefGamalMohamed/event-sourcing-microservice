package com.youssef.gamal.ecommerce.microservice.category.query.services;

import java.util.NoSuchElementException;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.common.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.query.configs.CachingConfigs;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.repos.CategoryViewRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = CachingConfigs.CATEGORY_VIEW_CACHE_NAME) // ✅ single source of truth for cache name
public class CategoryViewServiceImpl implements CategoryViewServiceIfc {

    private final CategoryViewRepo categoryRepo;

    @Override
    @Transactional
    @Caching(
        put = {
            // Cache by originalId (readable key, no static prefix)
            @CachePut(
                key = "'originalId=' + #result.originalId",
                condition = "#eventType.toString() == 'CREATED' || #eventType.toString() == 'UPDATED'"
            )
        },
        evict = {
            // Evict by originalId if deleted (readable key, no static prefix)
            @CacheEvict(
                key = "'originalId=' + #result.originalId",
                condition = "#eventType.toString() == 'DELETED'"
            )
        }
    )
    public CategoryView saveCategoryView(CategoryView categoryView, CategoryEventType eventType) {
        log.info("saveCategoryView called with categoryView: {} , eventType: {}", categoryView, eventType);

        categoryView.setEventType(eventType.toString()); // ✅ ensure DB consistency
        CategoryView savedCategoryView = categoryRepo.save(categoryView);

        log.info("[CATEGORY_VIEW:SAVE] snapshotId={}, originalId={}, eventType={}", 
                 savedCategoryView.getSnapshotId(), 
                 savedCategoryView.getOriginalId(), 
                 eventType);
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
    @Cacheable(
        key = "'originalId=' + #originalId",   // ✅ only dynamic key
        unless = "#result == null"
    )
    public CategoryView findByOriginalIdAndWithLastSnapshot(String originalId) {
        log.info("findByOriginalIdAndWithLastHistory called with originalId: {}", originalId);

        CategoryView categoryView = categoryRepo.findFirstByOriginalIdOrderByLastModifiedDateDesc(originalId)
                .orElseThrow(() -> new NoSuchElementException("Category view not found with originalId: " + originalId));

        // ✅ Return 404 if the last event was DELETE
        if (CategoryEventType.DELETED.toString().equalsIgnoreCase(categoryView.getEventType())) {
            log.warn("Category with originalId {} was deleted. Throwing NoSuchElementException", originalId);
            throw new NoSuchElementException("Category view not found with originalId: " + originalId);
        }

        log.info("CategoryView found with originalId: {}, snapshotId: {}", originalId, categoryView.getSnapshotId());
        return categoryView;
    }

    @Override
    @Cacheable(
        key = "'snapshotId=' + #snapshotId",
        unless = "#result == null"
    )
    public CategoryView findBySnapshotId(String snapshotId) {
        log.info("findBySnapshotId called with snapshotId: {}", snapshotId);

        return categoryRepo.findBySnapshotId(snapshotId)
                .orElseThrow(() -> new NoSuchElementException("Category view not found with snapshotId: " + snapshotId));
    }

    @Override
    @Cacheable(
        key = "'originalId=' + #originalId + ':snapshotId=' + #snapshotId",
        unless = "#result == null"
    )
    public CategoryView findByOriginalIdAndSnapShotId(String originalId, String snapshotId) {
        log.info("findByOriginalIdAndSnapShotId called with originalId: {}, snapshotId: {}", originalId, snapshotId);

        return categoryRepo.findByOriginalIdAndSnapshotId(originalId, snapshotId)
                .orElseThrow(() -> new NoSuchElementException(
                    "Category view not found with originalId: " + originalId + " and snapshotId: " + snapshotId
                ));
    }
}
