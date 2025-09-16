package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;

import java.util.Set;

public interface CategoryQueryIntegrationServiceIfc {

    // ---------- QUERY ----------

    // 1. Get snapshot by snapshotId
    CategoryQueryResponse findBySnapshotId(String snapshotId);

    // 2. Get snapshot by originalId + snapshotId
    CategoryQueryResponse findByOriginalIdAndSnapshotId(String originalId, String snapshotId);

    // 3. Batch fetch by snapshot IDs
    Set<CategoryQueryResponse> findAllBySnapshotIds(Set<String> snapshotIds);
}
