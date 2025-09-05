package com.youssef.gamal.ecommerce.microservice.product.query.services;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductViewService {

    ProductView savedProductView(ProductView productView, ProductEventType productEventType);

    Page<ProductView> findAllByOriginalId(String id, Pageable pageable);

    ProductView findByOriginalIdAndWithLastHistory(String originalId);

}
