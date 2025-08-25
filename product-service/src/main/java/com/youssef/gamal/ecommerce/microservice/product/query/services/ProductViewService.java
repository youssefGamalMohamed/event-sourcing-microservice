package com.youssef.gamal.ecommerce.microservice.product.query.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.youssef.gamal.ecommerce.microservice.product.query.models.ProductView;

public interface ProductViewService {

    ProductView addProductView(ProductView productView);

    Page<ProductView> findAllByOriginalId(String id, Pageable pageable);

    ProductView findByOriginalIdAndWithLastHistory(String originalId);

}
