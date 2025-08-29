package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.youssef.gamal.ecommerce.microservice.category.commands.dtos.CategoryDto;
import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryServiceIfc categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        log.info("CategoryController -> save() called with categoryDto={}", categoryDto);

        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.save(category);
        CategoryDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> save() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable String id,
                                              @RequestBody CategoryDto categoryDto) {
        log.info("CategoryController -> update() called with id={}, categoryDto={}", id, categoryDto);

        Category updatedCategory = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.update(id, updatedCategory);
        CategoryDto savedDto = categoryMapper.toDto(savedCategory);

        log.info("CategoryController -> update() completed successfully with result={}", savedDto);
        return ResponseEntity.ok(savedDto);
    }
}
