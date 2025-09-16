package com.youssef.gamal.ecommerce.microservice.product.query.events.consumers;

import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
import com.youssef.gamal.ecommerce.microservice.product.query.mappers.ProductViewMapper;
import com.youssef.gamal.ecommerce.microservice.product.query.services.ProductViewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ProductEventConsumerServiceImpl {

    private final ProductViewService productViewService;
    private final ProductViewMapper productViewMapper;

    public ProductEventConsumerServiceImpl(ProductViewService productViewService,
                                           ProductViewMapper productViewMapper) {
        this.productViewService = productViewService;
        this.productViewMapper = productViewMapper;
    }

    @KafkaListener(
            topics = "${messaging-queues.kafka.topics.products.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeProductEvent(ConsumerRecord<String, ProductEvent> record) {
        ProductEvent productEvent = record.value();

        // Log all important metadata
        log.info("📥 Received Product Event");
        log.info("   🔑 Key       : {}", record.key());
        log.info("   📦 Payload   : {}", productEvent);
        log.info("   📂 Partition : {}", record.partition());
        log.info("   📌 Offset    : {}", record.offset());
        log.info("   ⏱  Timestamp : {}", record.timestamp());
        log.info("   🏷  Headers   :");
        for (Header header : record.headers()) {
            log.info("      {} = {}",
                    header.key(),
                    new String(header.value(), StandardCharsets.UTF_8));
        }

        // Save Received Product Event to DB
        ProductEventType.fromValue(productEvent.getEventType())
                .ifPresentOrElse(eventType -> {
                            ProductView view = productViewMapper.toProductView(productEvent);
                            log.info("After convert to ProductView , product-view = {}", view);
                            productViewService.savedProductView(view, eventType);
                        },
                        () -> {
                            log.error("❌ Invalid event type: {} , originalId: {}, snapshotId: {}",
                                    productEvent.getEventType(), productEvent.getOriginalId(), productEvent.getSnapshotId());
                            // TODO: forward invalid event to dead-letter topic or monitoring system
                        }
                );
    }
}
