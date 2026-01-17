package com.youssef.gamal.ecommerce.microservice.category.commands.controllers;

import com.youssef.gamal.ecommerce.microservice.category.commands.constants.LogMessagesConstants;
import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import com.youssef.gamal.ecommerce.microservice.category.commands.mappers.CategoryMapper;
import com.youssef.gamal.ecommerce.microservice.category.commands.models.CreateCommand;
import com.youssef.gamal.ecommerce.microservice.category.commands.models.UpdateCommand;
import com.youssef.gamal.ecommerce.microservice.category.commands.services.CategoryServiceIfc;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class RESTCommandControllerImpl implements RESTCommandControllerIfc {

    private final CategoryServiceIfc service;
    private final CategoryMapper mapper;

    @Override
    public ResponseEntity<CreateCommand> save(@Valid @RequestBody CreateCommand createCommand) {
        log.info(LogMessagesConstants.CREATE_COMMAND_START, createCommand);

        Category category = mapper.toEntity(createCommand);
        Category saved_category = service.save(category);
        CreateCommand create_command_response = mapper.toCreateCommandResponse(saved_category);

        URI location = URI.create("/categories/" + saved_category.getId());
        return ResponseEntity.created(location).body(create_command_response);
    }

    @Override
    public ResponseEntity<UpdateCommand> update(String id, @Valid @RequestBody UpdateCommand updateCommand) {
        log.info(LogMessagesConstants.UPDATE_COMMAND_START, id, updateCommand);

        Category entity_from_request = mapper.toEntity(updateCommand);
        Category updated_category = service.update(id, entity_from_request);
        UpdateCommand update_command_response = mapper.toUpdateCommand(updated_category);

        return ResponseEntity.ok(update_command_response);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        log.info(LogMessagesConstants.DELETE_COMMAND_START, id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
