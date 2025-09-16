package com.youssef.gamal.ecommerce.microservice.product.command.events.consumers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.product.command.entities.Product;
import com.youssef.gamal.ecommerce.microservice.product.command.services.ProductServiceIfc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryEventConsumerServiceImpl {

    private final ProductServiceIfc productServiceIfc;


    @KafkaListener(
            topics = "${messaging-queues.kafka.topics.categories.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeCategoryEvent(ConsumerRecord<String, CategoryEvent> record) {
    	CategoryEvent categoryEvent = record.value();

        // Log all important metadata
        log.info("📥 Received Category Event");
        log.info("   🔑 Key       : {}", record.key());
        log.info("   📦 Payload   : {}", categoryEvent);
        log.info("   📂 Partition : {}", record.partition());
        log.info("   📌 Offset    : {}", record.offset());
        log.info("   ⏱  Timestamp : {}", record.timestamp());
        log.info("   🏷  Headers   :");
        for (Header header : record.headers()) {
            log.info("      {} = {}",
                    header.key(),
                    new String(header.value(), StandardCharsets.UTF_8));
        }
        
        log.info("Will Route our Event to UPDATED or DELETED");
        switch (categoryEvent.getEventType()) {
	        case "UPDATED" -> handleCategoryUpdated(record);
	        case "DELETED" -> handleCategoryDeleted(record);
	        default -> log.info("Ignoring event type: {}", categoryEvent.getEventType());
        }
        
        
    }
    
    
    private void handleCategoryUpdated(ConsumerRecord<String, CategoryEvent> record) {
    	CategoryEvent event = record.value();
        log.info("🔄 Handling category update: {}", event);
    }

    private void handleCategoryDeleted(ConsumerRecord<String, CategoryEvent> record) {
    	CategoryEvent event = record.value();
        log.info("🗑 Handling category delete: {}", event);
//        List<Product> affectedProducts = productServiceIfc.removeCategoryFromAllProductsBy(event.getOriginalId());
//        
//        affectedProducts.forEach(product -> {
//        	
//        });
    }
    
}
