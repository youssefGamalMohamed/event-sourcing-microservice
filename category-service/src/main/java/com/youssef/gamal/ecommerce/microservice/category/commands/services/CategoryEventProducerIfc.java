package com.youssef.gamal.ecommerce.microservice.category.commands.services;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;

public interface CategoryEventProducerIfc {

    void publish(CategoryEvent productEvent);
}
