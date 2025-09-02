package com.youssef.gamal.ecommerce.microservice.product.command.services;

import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaProductEventProducerImpl implements ProductEventProducerIfc {

   @Value("${broker.topics.products-topic}")
   private String topicName;

   private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

   public KafkaProductEventProducerImpl(KafkaTemplate<String, ProductEvent> kafkaTemplate) {
       this.kafkaTemplate = kafkaTemplate;
   }

   @Override
   public void publish(ProductEvent productEvent) {
       log.info("Publishing product event: {}", productEvent);
       
       // Use productId (or UUID) as the key to ensure partitioning consistency
       String messageKey = productEvent.getId() != null ? productEvent.getId() : UUID.randomUUID().toString();

       CompletableFuture<SendResult<String, ProductEvent>> future =
               kafkaTemplate.send(topicName, messageKey, productEvent);

       future.whenComplete((result, ex) -> {
           if (ex == null) {
               log.info("✅ Sent message=[{}] with offset=[{}]",
                       productEvent, result.getRecordMetadata().offset());
           } else {
               log.error("❌ Unable to send message=[{}] due to: {}",
                       productEvent, ex.getMessage());
           }
       });
   }
}
