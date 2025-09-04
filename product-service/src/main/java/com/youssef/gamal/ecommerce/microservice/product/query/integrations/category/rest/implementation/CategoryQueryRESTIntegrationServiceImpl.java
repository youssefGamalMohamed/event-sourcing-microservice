package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client.CategoryQueryRESTFeignClient;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryQueryRESTIntegrationServiceImpl implements CategoryQueryIntegrationServiceIfc {

    private final CategoryQueryRESTFeignClient categoryQueryRESTFeignClient;

    // ---------- QUERY ----------

    @Override
    public CategoryViewDto findCategoryById(String id) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryById() called with id={}", id);
        ResponseEntity<CategoryViewDto> response = categoryQueryRESTFeignClient.findByOriginalIdAndWithLastHistory(id);
        log.info("Category found: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public Page<CategoryViewDto> findCategoryHistoryById(String id, Pageable pageable) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryHistoryById() called with id={}, pageable={}", id, pageable);
        ResponseEntity<Page<CategoryViewDto>> response = categoryQueryRESTFeignClient.findAllHistoryByOriginalId(id, pageable);
        log.info("Category history retrieved for id={} with totalElements={}", id, response.getBody().getTotalElements());
        return response.getBody();
    }
}
