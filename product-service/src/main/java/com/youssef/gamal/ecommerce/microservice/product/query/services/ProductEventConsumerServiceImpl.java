package com.youssef.gamal.ecommerce.microservice.product.query.services;

import lombok.extern.slf4j.Slf4j;

//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.query.mappers.ProductViewMapper;
import com.youssef.gamal.ecommerce.microservice.product.shared.events.ProductEvent;

@Service
@Slf4j
public class ProductEventConsumerServiceImpl {

    private final ProductViewService productViewService;
    private final ProductViewMapper productViewMapper;

    public ProductEventConsumerServiceImpl(ProductViewService productViewService, ProductViewMapper productViewMapper) {
        this.productViewService = productViewService;
        this.productViewMapper = productViewMapper;
    }

    @KafkaListener(topics = "${kafka.event-names.product-event}", groupId = "${spring.kafka.consumer.group-id}")
//    @RabbitListener(queues = {"${rabbitmq.queues-names.product_queue}"})
    public void consumeProductEvent(ProductEvent productEvent) {
        log.info("✅ Received product event: {}", productEvent);
        // Save Received Product Event to DB
        productViewService.addProductView(productViewMapper.toProductView(productEvent));
    }


}
