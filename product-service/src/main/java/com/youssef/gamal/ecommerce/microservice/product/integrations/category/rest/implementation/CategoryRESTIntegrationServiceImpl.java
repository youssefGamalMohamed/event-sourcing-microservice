package com.youssef.gamal.ecommerce.microservice.product.integrations.category.rest.implementation;

import com.youssef.gamal.ecommerce.microservice.product.integrations.category.rest.client.CategoryRESTFeignClient;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommandDto;
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
public class CategoryRESTIntegrationServiceImpl implements CategoryIntegrationServiceIfc {

    private final CategoryRESTFeignClient categoryRESTFeignClient;

    // ---------- QUERY ----------

    @Override
    public CategoryViewDto findCategoryById(String id) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryById() called with id={}", id);
        ResponseEntity<CategoryViewDto> response = categoryRESTFeignClient.findByOriginalIdAndWithLastHistory(id);
        log.info("Category found: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public Page<CategoryViewDto> findCategoryHistoryById(String id, Pageable pageable) {
        log.info("CategoryRESTIntegrationServiceImpl -> findCategoryHistoryById() called with id={}, pageable={}", id, pageable);
        ResponseEntity<Page<CategoryViewDto>> response = categoryRESTFeignClient.findAllHistoryByOriginalId(id, pageable);
        log.info("Category history retrieved for id={} with totalElements={}", id, response.getBody().getTotalElements());
        return response.getBody();
    }

    // ---------- COMMAND ----------

    @Override
    public CategoryCommandDto saveCategory(CategoryCommandDto categoryDto) {
        log.info("CategoryRESTIntegrationServiceImpl -> saveCategory() called with categoryDto={}", categoryDto);
        ResponseEntity<CategoryCommandDto> response = categoryRESTFeignClient.save(categoryDto);
        log.info("Category saved successfully: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public CategoryCommandDto updateCategory(String id, CategoryCommandDto categoryDto) {
        log.info("CategoryRESTIntegrationServiceImpl -> updateCategory() called with id={}, categoryDto={}", id, categoryDto);
        ResponseEntity<CategoryCommandDto> response = categoryRESTFeignClient.update(id, categoryDto);
        log.info("Category updated successfully: {}", response.getBody());
        return response.getBody();
    }

    @Override
    public void deleteCategory(String id) {
        log.info("CategoryRESTIntegrationServiceImpl -> deleteCategory() called with id={}", id);
        categoryRESTFeignClient.delete(id);
        log.info("Category deleted successfully with id={}", id);
    }
}
