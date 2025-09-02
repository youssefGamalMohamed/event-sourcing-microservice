package com.youssef.gamal.ecommerce.microservice.product.integrations.category.rest.client;

import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.commands.CategoryCommandDto;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryViewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "category-service", path = "/ecommerce/api/v1")
public interface CategoryRESTFeignClient {

    // ---------- COMMAND SIDE ----------

    @PostMapping("/categories")
    ResponseEntity<CategoryCommandDto> save(@RequestBody CategoryCommandDto categoryCommandDto);

    @PutMapping("/categories/{id}")
    ResponseEntity<CategoryCommandDto> update(@PathVariable(name = "id") String id, @RequestBody CategoryCommandDto categoryCommandDto);


    @DeleteMapping("/categories/{id}")
    ResponseEntity<Void> delete(@PathVariable(name = "id") String id);


    // ---------- QUERY SIDE ----------

    @GetMapping("/categories/{id}")
    ResponseEntity<CategoryViewDto> findByOriginalIdAndWithLastHistory(@PathVariable(name = "id") String id);

    @GetMapping("/categories/{id}/history")
    ResponseEntity<Page<CategoryViewDto>> findAllHistoryByOriginalId(@PathVariable(name = "id") String id, Pageable pageable);
}
