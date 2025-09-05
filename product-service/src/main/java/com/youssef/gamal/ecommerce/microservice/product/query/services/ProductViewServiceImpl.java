package com.youssef.gamal.ecommerce.microservice.product.query.services;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation.CategoryQueryIntegrationServiceIfc;
import com.youssef.gamal.ecommerce.microservice.product.query.repos.ProductViewRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "productViewCache") // ✅ single source of truth for cache name
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepo productViewRepo;
    private final CategoryQueryIntegrationServiceIfc categoryIntegrationService;

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
                    @CacheEvict(key = "#productView.originalId", condition = "#eventType.toString() == 'DELETED'")
            }
    )
    public ProductView savedProductView(ProductView productView, ProductEventType eventType) {
        log.info("savedProductView called with productView: {} , eventType: {}", productView, eventType);

        productView.setEventType(eventType.toString()); // ✅ ensure DB consistency
        ProductView savedProductView = productViewRepo.save(productView);

        log.info("[PRODUCT_VIEW:SAVE] id={}, originalId={}, eventType={}",
                savedProductView.getId(), savedProductView.getOriginalId(), eventType);

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

        log.info("ProductView found with originalId: {}, id: {}", originalId, productView.getId());
        return productView;
    }
}
