package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client.CategoryQueryRESTFeignClient;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryQueryRESTIntegrationServiceImpl implements CategoryQueryIntegrationServiceIfc {

    private final CategoryQueryRESTFeignClient categoryQueryRESTFeignClient;

    @Override
    public CategoryQueryResponse findBySnapshotId(String snapshotId) {
        log.info("findBySnapshotId(snapshotId={})", snapshotId);
        ResponseEntity<CategoryQueryResponse> response =
                categoryQueryRESTFeignClient.findBySnapshotId(snapshotId);
        return response.getBody();
    }

    @Override
    public CategoryQueryResponse findByOriginalIdAndSnapshotId(String originalId, String snapshotId) {
        log.info("findByOriginalIdAndSnapshotId(originalId={}, snapshotId={})", originalId, snapshotId);
        ResponseEntity<CategoryQueryResponse> response =
                categoryQueryRESTFeignClient.findByOriginalIdAndSnapshotId(originalId, snapshotId);
        return response.getBody();
    }

    @Override
    public Set<CategoryQueryResponse> findAllBySnapshotIds(Set<String> snapshotIds) {
        log.info("findAllBySnapshotIds({})", snapshotIds);
        return snapshotIds.stream()
                .map(this::findBySnapshotId) // reuse wrapper method
                .collect(Collectors.toSet());
    }
}
