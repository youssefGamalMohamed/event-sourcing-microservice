package com.youssef.gamal.ecommerce.microservice.product.query.services;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;

public interface ProductViewService {

    ProductView savedProductView(ProductView productView, ProductEventType productEventType);

    Page<ProductView> findAllByOriginalId(String id, Pageable pageable);

    ProductView findByOriginalIdAndWithLastHistory(String originalId);

}
