package com.youssef.gamal.ecommerce.microservice.category.commands.events.producers;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;

import brave.Tracer;
import io.micrometer.observation.annotation.Observed;
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

    @Value("${messaging-queues.kafka.topics.categories.name}")
    private String categoriesTopic;
    
    private final Tracer tracer;
    
    private final KafkaTemplate<String, CategoryEvent> kafkaTemplate;

    public KafkaCategoryEventProducerImpl(KafkaTemplate<String, CategoryEvent> kafkaTemplate, Tracer tracer) {
        this.tracer = tracer;
		this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Observed(name = "kafka.produce.category", contextualName = "publish-category-event")
    public void publish(CategoryEvent categoryEvent) {
        log.info("📤 Publishing category event: {}", categoryEvent);

        // add UUID generated for each message
        String messageKey = UUID.randomUUID().toString();
        log.info("Event Message Key = {}", messageKey);
        
        // add to distributed tracing current span for pushing event with the message-key
        tracer.currentSpan().annotate("Push Event to Kafka in " + categoriesTopic + " Topic With Message-Key = " + messageKey);
        
        CompletableFuture<SendResult<String, CategoryEvent>> future =
                kafkaTemplate.send(categoriesTopic, messageKey, categoryEvent);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ Successfully sent message=[{}] to topic={} with offset=[{}]",
                        categoryEvent, categoriesTopic, result.getRecordMetadata().offset());
            } else {
                log.error("❌ Failed to send message=[{}] to topic={} due to: {}",
                        categoryEvent, categoriesTopic, ex.getMessage(), ex);
            }
        });
    }
}
