package com.youssef.gamal.ecommerce.microservice.category.commands.services;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaCategoryEventProducerImpl implements CategoryEventProducerIfc {

    @Value("${broker.topics.categories-topic}")
    private String topicName;

    private final KafkaTemplate<String, CategoryEvent> kafkaTemplate;

    public KafkaCategoryEventProducerImpl(KafkaTemplate<String, CategoryEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(CategoryEvent categoryEvent) {
        log.info("Publishing product event: {}", categoryEvent);

        // Use productId (or UUID) as the key to ensure partitioning consistency
        String messageKey = categoryEvent.getId() != null ? categoryEvent.getId() : UUID.randomUUID().toString();

        CompletableFuture<SendResult<String, CategoryEvent>> future =
                kafkaTemplate.send(topicName, messageKey, categoryEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ Sent message=[{}] with offset=[{}]",
                        categoryEvent, result.getRecordMetadata().offset());
            } else {
                log.error("❌ Unable to send message=[{}] due to: {}",
                        categoryEvent, ex.getMessage());
            }
        });
    }
}
