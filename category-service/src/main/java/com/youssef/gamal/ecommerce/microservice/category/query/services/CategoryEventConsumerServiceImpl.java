package com.youssef.gamal.ecommerce.microservice.category.query.services;

import com.youssef.gamal.ecommerce.microservice.category.commands.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.models.CategoryView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryEventConsumerServiceImpl {

    private final CategoryViewServiceIfc categoryViewServiceIfc;
    private final CategoryViewMapper categoryViewMapper;

    @KafkaListener(topics = "${broker.topics.categories-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCategoryEvent(CategoryEvent categoryEvent) {
        log.info("✅ Received category event: {}", categoryEvent);
        CategoryEventType.fromValue(categoryEvent.getEventType())
                .ifPresentOrElse(eventType -> {
                    CategoryView view = categoryViewMapper.toView(categoryEvent);
                    categoryViewServiceIfc.saveCategoryView(view, eventType);
                }, () -> {
                    log.error("❌ Invalid event type: {} , id: {}", categoryEvent.getEventType(), categoryEvent.getId());
                    // TODO: send event to kafka to send email for admin to detect non-normal behaviours for event validations
                }
        );

    }


}
