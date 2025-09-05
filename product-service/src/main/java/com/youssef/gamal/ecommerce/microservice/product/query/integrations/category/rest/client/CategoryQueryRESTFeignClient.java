package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "category-service", path = "/ecommerce/api/v1")
public interface CategoryQueryRESTFeignClient {

    // ---------- QUERY SIDE ----------
    @GetMapping("/categories/{id}")
    ResponseEntity<CategoryViewDto> findByOriginalIdAndWithLastHistory(@PathVariable(name = "id") String id);

    @GetMapping("/categories/{id}/history")
    ResponseEntity<Page<CategoryViewDto>> findAllHistoryByOriginalId(@PathVariable(name = "id") String id, Pageable pageable);
}
