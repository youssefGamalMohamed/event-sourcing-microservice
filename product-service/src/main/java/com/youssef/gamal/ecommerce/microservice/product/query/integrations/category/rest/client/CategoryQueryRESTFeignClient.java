package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

@FeignClient(value = "category-service", path = "/ecommerce/api/v1")
public interface CategoryQueryRESTFeignClient {

    // ---------- QUERY SIDE ----------

    // 1. Get snapshot by snapshotId (query param)
    @GetMapping(value = "/categories", params = "snapshotId")
    ResponseEntity<CategoryQueryResponse> findBySnapshotId(
            @RequestParam("snapshotId") String snapshotId);

    // 2. Get snapshot by originalId + snapshotId
    @GetMapping("/categories/{originalId}/snapshots/{snapshotId}")
    ResponseEntity<CategoryQueryResponse> findByOriginalIdAndSnapshotId(
            @PathVariable("originalId") String originalId,
            @PathVariable("snapshotId") String snapshotId);
}
