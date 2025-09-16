package com.youssef.gamal.ecommerce.microservice.category.query.events.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.category.common.enums.CategoryEventType;
import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.category.query.entities.CategoryView;
import com.youssef.gamal.ecommerce.microservice.category.query.mappers.CategoryViewMapper;
import com.youssef.gamal.ecommerce.microservice.category.query.services.CategoryViewServiceIfc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryEventConsumerServiceImpl {

    private final CategoryViewServiceIfc categoryViewServiceIfc;
    private final CategoryViewMapper categoryViewMapper;

    @KafkaListener(
            topics = "${messaging-queues.kafka.topics.categories.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeCategoryEvent(ConsumerRecord<String, CategoryEvent> record) {
        CategoryEvent categoryEvent = record.value();

        // 🔑 Log important Kafka metadata
        log.info("✅ Received category event: {} | key={} | partition={} | offset={} | headers={}",
                categoryEvent,
                record.key(),
                record.partition(),
                record.offset(),
                record.headers()
        );

        CategoryEventType.fromValue(categoryEvent.getEventType())
                .ifPresentOrElse(eventType -> {
                            CategoryView view = categoryViewMapper.toView(categoryEvent);
                            categoryViewServiceIfc.saveCategoryView(view, eventType);
                        },
                        () -> {
                            log.error("❌ Invalid event type received: {} , original_id: {} | snapshot_id = {} | key={} | partition={} | offset={}",
                                    categoryEvent.getEventType(),
                                    categoryEvent.getOriginalId(),
                                    categoryEvent.getSnapshotId(),
                                    record.key(),
                                    record.partition(),
                                    record.offset()
                            );
                            // 🚫 Do not push back invalid events, only log them
                        }
                );
    }
}
