package com.youssef.gamal.ecommerce.microservice.category.commands.events.producers;

import brave.Tracer;
import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;
import com.youssef.gamal.ecommerce.microservice.category.shared.exceptions.EventPublishException;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommandEventProducerImpl implements CommandEventProducerIfc {

    @Value("${messaging-queues.kafka.topics.categories.name}")
    private String categoriesTopic;

    @Value("${kafka.producer.timeout-seconds:10}")
    private long timeoutSeconds;

    @Value("${retry.kafka.max-attempts:3}")
    private int retryAttempts;


    private final Tracer tracer;
    private final KafkaTemplate<String, CategoryEvent> kafkaTemplate;

    @Override
    @Observed(name = "kafka.produce.category", contextualName = "publish-category-event")
    @Retryable(
            retryFor = {EventPublishException.class},  // ✅ Only retry EventPublishException
            maxAttemptsExpression = "${retry.kafka.max-attempts:3}",
            backoff = @Backoff(
                    delayExpression = "${retry.kafka.initial-interval:2000}",
                    multiplierExpression = "${retry.kafka.multiplier:2.0}",
                    maxDelayExpression = "${retry.kafka.max-interval:10000}"
            )
    )
    public void publish(CategoryEvent categoryEvent) {
        String messageKey = UUID.randomUUID().toString();

        log.info("📤 [Attempt] Publishing event: {} with key: {}",
                categoryEvent.getEventType(), messageKey);

        try {
            // Add tracing
            if (tracer.currentSpan() != null) {
                tracer.currentSpan()
                        .tag("message.key", messageKey)
                        .tag("topic", categoriesTopic)
                        .annotate("Kafka publish attempt");
            }

            // Synchronous send with timeout
            CompletableFuture<SendResult<String, CategoryEvent>> future =
                    kafkaTemplate.send(categoriesTopic, messageKey, categoryEvent);

            SendResult<String, CategoryEvent> result = future.get(timeoutSeconds, TimeUnit.SECONDS);

            log.info("✅ Event published successfully: offset={}, partition={}",
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());

        } catch (ExecutionException e) {
            log.error("❌ Kafka execution error: {}", e.getCause().getMessage());
            throw new EventPublishException("Failed to publish event to Kafka", e.getCause());

        } catch (TimeoutException e) {
            log.error("⏱️ Kafka publish timeout after {}s", timeoutSeconds);
            throw new EventPublishException("Kafka publish timeout", e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("🔴 Thread interrupted during publish");
            throw new EventPublishException("Event publishing interrupted", e);
        }
    }

    @Recover
    public void recover(EventPublishException e, CategoryEvent categoryEvent) {
        log.error("🔥 All {} attempts exhausted for event: {}", retryAttempts, categoryEvent.getEventType());

        // ✅ Just re-throw - don't try to return anything
        throw e;
    }
}