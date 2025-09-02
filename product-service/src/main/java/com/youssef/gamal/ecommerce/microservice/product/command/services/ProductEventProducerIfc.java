package com.youssef.gamal.ecommerce.microservice.product.command.services;


import com.youssef.gamal.ecommerce.microservice.product.infrastructure.kafka.events.ProductEvent;

public interface ProductEventProducerIfc {

    void publish(ProductEvent productEvent);
}
