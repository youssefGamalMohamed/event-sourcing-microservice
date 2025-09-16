package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "category-service", path = "/ecommerce/api/v1")
public interface CategoryQueryRESTFeignClient {

    // ---------- QUERY SIDE ----------
    @GetMapping("/categories/{snapshotId}")
    ResponseEntity<CategoryQueryResponse> findByOriginalIdAndWithLastHistory(@PathVariable(name = "snapshotId") String id);

    @GetMapping("/categories/{snapshotId}/history")
    ResponseEntity<Page<CategoryQueryResponse>> findAllHistoryByOriginalId(@PathVariable(name = "snapshotId") String id, Pageable pageable);
}
