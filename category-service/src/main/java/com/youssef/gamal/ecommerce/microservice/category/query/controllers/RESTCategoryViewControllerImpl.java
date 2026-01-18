package com.youssef.gamal.ecommerce.microservice.category.query.controllers;

import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.services.CategoryViewServiceIfc;
import com.youssef.gamal.ecommerce.microservice.shared.module.rest.dtos.category.query.CategoryQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class RESTCategoryViewControllerImpl implements RESTCategoryViewControllerIfc {

    private final CategoryViewServiceIfc categoryViewService;
    private final CategoryViewMapper categoryViewMapper;

    @GetMapping("/categories/{originalId}/snapshots")
    @Override
    public Page<CategoryQueryResponse> findAllSnapshotsByOriginalId(UUID originalId, Pageable pageable) {
        log.info("findAllSnapshotsByOriginalId originalId={}, pageable={}", originalId, pageable);
        return categoryViewService
                .findAllByOriginalId(originalId.toString(), pageable)
                .map(categoryViewMapper::toDto);
    }

    @GetMapping("/categories/{originalId}")
    @Override
    public CategoryQueryResponse findLatestSnapshotByOriginalId(UUID originalId) {
        log.info("findLatestSnapshotByOriginalId originalId={}", originalId);
        CategoryView view =
                categoryViewService.findByOriginalIdAndWithLastSnapshot(originalId.toString());
        return categoryViewMapper.toDto(view);
    }

    @GetMapping(value = "/categories", params = "snapshotId")
    @Override
    public CategoryQueryResponse findBySnapshotId(UUID snapshotId) {
        log.info("findBySnapshotId snapshotId={}", snapshotId);
        CategoryView view =
                categoryViewService.findBySnapshotId(snapshotId.toString());
        return categoryViewMapper.toDto(view);
    }

    @GetMapping("/categories/{originalId}/snapshots/{snapshotId}")
    @Override
    public CategoryQueryResponse findByOriginalIdAndSnapshotId(UUID originalId, UUID snapshotId) {
        log.info("findByOriginalIdAndSnapshotId originalId={}, snapshotId={}", originalId, snapshotId);
        CategoryView view =
                categoryViewService.findByOriginalIdAndSnapShotId(
                        originalId.toString(),
                        snapshotId.toString()
                );
        return categoryViewMapper.toDto(view);
    }
}
