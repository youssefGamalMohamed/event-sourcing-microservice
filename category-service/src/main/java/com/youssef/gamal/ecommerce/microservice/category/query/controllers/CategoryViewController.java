package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.gamal.ecommerce.microservice.category.commands.dtos.CategoryDto;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.models.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.services.CategoryViewServiceIfc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryViewController {


    private final CategoryViewServiceIfc categoryViewService;
    private final CategoryViewMapper categoryViewMapper;



    @GetMapping("/categories/{id}/history")
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryDto> findAllHistoryByOriginalId(@PathVariable(name = "id") String id, @ParameterObject Pageable pageable) {
        log.info("findById called with id: {},pageable:{}", id, pageable);
        Page<CategoryView> cagtegoryViewPage = categoryViewService.findAllByOriginalId(id, pageable);
        log.info("CategoryView found with id: {}, TotalElement:{}", id, cagtegoryViewPage.getTotalElements());
        return cagtegoryViewPage.map(categoryViewMapper::toDto);
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto findByOriginalIdAndWithLastHistory(@PathVariable(name = "id") String id) {
        log.info("findById called with id: {}", id);
        CategoryView catgegoryView = categoryViewService.findByOriginalIdAndWithLastHistory(id);
        log.info("CategoryView found with id: {}", id);
        return categoryViewMapper.toDto(catgegoryView);
    }
}
