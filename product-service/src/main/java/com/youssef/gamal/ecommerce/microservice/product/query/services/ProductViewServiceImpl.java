package com.youssef.gamal.ecommerce.microservice.product.query.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.query.models.ProductView;
import com.youssef.gamal.ecommerce.microservice.product.query.repos.ProductViewRepo;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProductViewServiceImpl implements ProductViewService {

    private final ProductViewRepo productViewRepo;

    public ProductViewServiceImpl(ProductViewRepo productViewRepo) {
        this.productViewRepo = productViewRepo;
    }

    @Override
    @CacheEvict(
            value = "productViewCache",
            key = "#result.originalId",
            condition = "#result.eventType == 'UPDATED' || #result.eventType == 'DELETED'"
    )
    public ProductView addProductView(ProductView productView) {
        log.info("addProductView called with productView: {}", productView);
        ProductView savedProductView = productViewRepo.save(productView);
        log.info("ProductView saved with id: {}", savedProductView.getId());
        return productView;
    }

    @Override
    public Page<ProductView> findAllByOriginalId(String originalId, Pageable pageable) {
        log.info("findAllByOriginalId called with originalId: {},pageable:{}", originalId, pageable);
        Page<ProductView> productViews = productViewRepo.findAllByOriginalId(originalId, pageable);
        log.info("ProductViews found with originalId: {}, count: {}", originalId, productViews.getTotalElements());
        return productViews;
    }

    @Override
    @Cacheable(value = "productViewCache", key = "#originalId")
    public ProductView findByOriginalIdAndWithLastHistory(String originalId) {
        log.info("findByOriginalIdAndWithLastHistory called with originalId: {}", originalId);
        ProductView productView = productViewRepo.findFirstByOriginalIdOrderByLastModifiedDateDesc(originalId)
                        .orElseThrow(() -> new NoSuchElementException("Product view not found with originalId: " + originalId));
        log.info("ProductView found with originalId: {}, id: {}", originalId, productView.getId());
        return productView;
    }
}
