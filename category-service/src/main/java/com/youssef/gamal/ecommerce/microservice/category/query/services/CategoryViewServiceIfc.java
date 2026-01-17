package com.youssef.gamal.ecommerce.microservice.category.query.services;

import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.shared.enums.CategoryEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryViewServiceIfc {

    CategoryView saveCategoryView(CategoryView categoryView, CategoryEventType eventType);

    Page<CategoryView> findAllByOriginalId(String id, Pageable pageable);

    CategoryView findByOriginalIdAndWithLastSnapshot(String originalId);
    
    CategoryView findBySnapshotId(String snapshotId);
    
    CategoryView findByOriginalIdAndSnapShotId(String originalId , String snapshotId);
}
