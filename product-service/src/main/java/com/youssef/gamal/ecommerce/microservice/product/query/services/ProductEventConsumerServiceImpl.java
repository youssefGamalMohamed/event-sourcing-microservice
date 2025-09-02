    package com.youssef.gamal.ecommerce.microservice.product.query.services;

    import com.youssef.gamal.ecommerce.microservice.product.common.enums.ProductEventType;
    import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;
    import com.youssef.gamal.ecommerce.microservice.product.query.entities.ProductView;
    import com.youssef.gamal.ecommerce.microservice.product.query.mappers.ProductViewMapper;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Service;

    @Service
    @Slf4j
    public class ProductEventConsumerServiceImpl {

        private final ProductViewService productViewService;
        private final ProductViewMapper productViewMapper;

        public ProductEventConsumerServiceImpl(ProductViewService productViewService, ProductViewMapper productViewMapper) {
            this.productViewService = productViewService;
            this.productViewMapper = productViewMapper;
        }

        @KafkaListener(topics = "${broker.topics.products-topic}", groupId = "${spring.kafka.consumer.group-id}")
        public void consumeProductEvent(ProductEvent productEvent) {
            log.info("✅ Received product event: {}", productEvent);
            // Save Received Product Event to DB
            ProductEventType.fromValue(productEvent.getEventType())
                    .ifPresentOrElse(eventType -> {
                        ProductView view = productViewMapper.toProductView(productEvent);
                        productViewService.savedProductView(view, eventType);
                    },
                    () -> {
                        log.error("❌ Invalid event type: {} , id: {}", productEvent.getEventType(), productEvent.getId());
                        // TODO: send event to kafka to send email for admin to detect non-normal behaviours for event validations
                    }
            );
        }


    }
