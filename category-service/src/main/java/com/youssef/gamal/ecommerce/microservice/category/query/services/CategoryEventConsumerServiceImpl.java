package com.youssef.gamal.ecommerce.microservice.category.query.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.shared.events.CategoryEvent;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryEventConsumerServiceImpl {

    private final CategoryViewServiceIfc categoryViewServiceIfc;
    private final CategoryViewMapper categoryViewMapper;

    @PostConstruct
    public void initiFunc() {
    	log.info("=".repeat(100));
    }

    @KafkaListener(topics = "${broker.topics.categories-topic}", groupId = "${spring.kafka.consumer.group-id}")
//    @RabbitListener(queues = {"${rabbitmq.queues-names.product_queue}"})
    public void consumeCategoryEvent(CategoryEvent categoryEvent) {
        log.info("✅ Received category event: {}", categoryEvent);
        // Save Received Category Event to DB
        categoryViewServiceIfc.addCategorytView(categoryViewMapper.toView(categoryEvent));
    }


}
