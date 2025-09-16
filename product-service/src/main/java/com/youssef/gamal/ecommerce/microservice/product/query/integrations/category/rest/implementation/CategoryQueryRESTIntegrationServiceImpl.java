package com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.product.query.integrations.category.rest.client.CategoryQueryRESTFeignClient;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

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
    public CategoryQueryResponse findCategoryById(String id) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryById() called with snapshotId={}", id);
        ResponseEntity<CategoryQueryResponse> response = categoryQueryRESTFeignClient.findByOriginalIdAndWithLastHistory(id);
        log.info("Category found: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public Page<CategoryQueryResponse> findCategoryHistoryById(String id, Pageable pageable) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryHistoryById() called with snapshotId={}, pageable={}", id, pageable);
        ResponseEntity<Page<CategoryQueryResponse>> response = categoryQueryRESTFeignClient.findAllHistoryByOriginalId(id, pageable);
        log.info("Category history retrieved for snapshotId={} with totalElements={}", id, response.getBody().getTotalElements());
        return response.getBody();
    }

	@Override
	public Set<CategoryQueryResponse> findAllByIds(Set<String> categoryIds) {
		log.info("findAllByIds({})", categoryIds);
		return categoryIds.stream()
				.map(id -> categoryQueryRESTFeignClient.findByOriginalIdAndWithLastHistory(id))
				.map(responseEntity -> responseEntity.getBody())
				.peek(categoryViewDto -> log.info("Recieved CategoryQueryResponse Response with originalId = " + categoryViewDto.originalId()))
				.collect(Collectors.toSet());
	}
}
