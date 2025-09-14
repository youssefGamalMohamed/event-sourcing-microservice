package com.youssef.gamal.ecommerce.microservice.product.command.events.producers;

import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
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
public class KafkaProductEventProducerImpl implements ProductEventProducerIfc {

	@Value("${messaging-queues.kafka.topics.products.name}")
    private String productsTopic;

    private final Tracer tracer;
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public KafkaProductEventProducerImpl(KafkaTemplate<String, ProductEvent> kafkaTemplate,
                                         Tracer tracer) {
        this.kafkaTemplate = kafkaTemplate;
        this.tracer = tracer;
    }

    @Override
    @Observed(name = "kafka.produce.product", contextualName = "publish-product-event")
    public void publish(ProductEvent productEvent) {
        // Generate unique message key
        String messageKey = UUID.randomUUID().toString();

        // Log publishing
        log.info("📤 Publishing product event with key={} : {}", messageKey, productEvent);

        // Add distributed tracing annotation
        if (tracer.currentSpan() != null) {
            tracer.currentSpan().annotate("Push Event to Kafka topic=" + productsTopic + " key=" + messageKey);
        }

        // Send event asynchronously
        CompletableFuture<SendResult<String, ProductEvent>> future =
                kafkaTemplate.send(productsTopic, messageKey, productEvent);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("✅ Successfully sent product event with key={} to topic={} partition={} offset={}",
                        messageKey,
                        productsTopic,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("❌ Failed to send product event with key={} to topic={} due to: {}",
                        messageKey, productsTopic, ex.getMessage(), ex);
            }
        });
    }
}
