package com.youssef.gamal.ecommerce.microservice.product.query.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.query.configs.CachingConfigs;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation.CategoryQueryIntegrationServiceIfc;
import com.youssef.gamal.ecommerce.microservice.product.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.product.query.repos.ProductViewRepo;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = CachingConfigs.PRODUCT_VIEW_CACHE_NAME) // ✅ single source of truth for cache name
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepo productViewRepo;
    private final CategoryQueryIntegrationServiceIfc categoryIntegrationService;
    private final CategoryViewMapper categoryViewMapper;
    
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
                    @CacheEvict(key = "#result.originalId", condition = "#eventType.toString() == 'DELETED'")
            }
    )
    public ProductView savedProductView(ProductView productView, ProductEventType eventType) {
        log.info("savedProductView called with productView: {} , eventType: {}", productView, eventType);

        productView.setEventType(eventType.toString()); // ✅ ensure DB consistency
                
       
        Set<CategoryQueryResponse> categoryQueryResponses = productView.getCategories().stream()
        		.map(categoryRef -> categoryIntegrationService.findByOriginalIdAndSnapshotId(categoryRef.getOriginalId(), categoryRef.getSnapshotId()))
        		.collect(Collectors.toSet());
        
        productView.setCategories(categoryViewMapper.toEntities(categoryQueryResponses));
        
        ProductView savedProductView = productViewRepo.save(productView);

        log.info("[PRODUCT_VIEW:SAVE] snapshotId={}, originalId={}, eventType={}",
                savedProductView.getSnapshotId(), savedProductView.getOriginalId(), eventType);

        return savedProductView;
    }

    @Override
    public Page<ProductView> findAllByOriginalId(String originalId, Pageable pageable) {
        log.info("findAllByOriginalId called with originalId: {}, pageable: {}", originalId, pageable);
        Page<ProductView> productViews = productViewRepo.findAllByOriginalId(originalId, pageable);
        log.info("ProductViews found with originalId: {}, count: {}", originalId, productViews.getTotalElements());

        return productViews;
    }

    @Override
    @Cacheable(key = "#originalId", unless = "#result == null") // ✅ cache element when result != null
    public ProductView findByOriginalIdAndWithLastHistory(String originalId) {
        log.info("findByOriginalIdAndWithLastHistory called with originalId: {}", originalId);

        ProductView productView = productViewRepo.findFirstByOriginalIdOrderByLastModifiedDateDesc(originalId)
                .orElseThrow(() -> new NoSuchElementException("Product view not found with originalId: " + originalId));

        // ✅ Return 404 if the last event was DELETE.
        // This leverages the shared RestExceptionHandler to map NoSuchElementException to a 404 response.
        if (ProductEventType.DELETED.toString().equalsIgnoreCase(productView.getEventType())) {
            log.warn("Product with originalId {} was deleted. Throwing NoSuchElementException", originalId);
            throw new NoSuchElementException("Product view not found with originalId: " + originalId);
        }

        log.info("ProductView found with originalId: {}, snapshotId: {}", originalId, productView.getSnapshotId());
        return productView;
    }
}
