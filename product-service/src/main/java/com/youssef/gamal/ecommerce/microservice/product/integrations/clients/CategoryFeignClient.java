package com.youssef.gamal.ecommerce.microservice.product.integrations.clients;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/ecommerce/api/v1")
public interface CategoryFeignClient {

    @GetMapping("/categories/{id}")
    ResponseEntity<CategoryViewDto> findByOriginalIdAndWithLastHistory(@PathVariable(name = "id") String id);

}
