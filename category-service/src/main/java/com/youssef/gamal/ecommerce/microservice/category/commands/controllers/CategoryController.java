package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shart_module.rest.dtos.category.commands.CategoryCommandDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryServiceIfc categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/categories")
    public ResponseEntity<CategoryCommandDto> save(@RequestBody CategoryCommandDto categoryCommandDto) {
        log.info("CategoryController -> save() called with categoryDto={}", categoryCommandDto);

        Category category = categoryMapper.toEntity(categoryCommandDto);
        Category savedCategory = categoryService.save(category);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> save() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryCommandDto> update(@PathVariable(name = "id") String id,
                                              @RequestBody CategoryCommandDto categoryDto) {
        log.info("CategoryController -> update() called with id={}, categoryDto={}", id, categoryDto);

        Category updatedCategory = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.update(id, updatedCategory);
        CategoryCommandDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> update() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }
}
