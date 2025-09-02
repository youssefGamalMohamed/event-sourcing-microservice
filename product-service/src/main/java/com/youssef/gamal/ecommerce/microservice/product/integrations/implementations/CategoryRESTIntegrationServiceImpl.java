package com.youssef.gamal.ecommerce.microservice.product.integrations.implementations;

import com.youssef.gamal.ecommerce.microservice.product.integrations.clients.CategoryFeignClient;
import com.youssef.gamal.ecommerce.microservice.product.integrations.interfaces.CategoryIntegrationServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryRESTIntegrationServiceImpl implements CategoryIntegrationServiceIfc {

    private final CategoryFeignClient categoryFeignClient;

    @Override
    public CategoryViewDto findCategoryById(String id) {
        log.info("Find category by id {}", id);
        ResponseEntity<CategoryViewDto> responseEntity =categoryFeignClient.findByOriginalIdAndWithLastHistory(id);
        log.info("Find category by id {}", responseEntity.getBody());
        return responseEntity.getBody();
    }


}
