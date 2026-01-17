package com.youssef.gamal.ecommerce.microservice.category.commands.events.producers;

import com.youssef.gamal.ecommerce.microservice.category.infrastructure.kafka.events.CategoryEvent;

public interface CommandEventProducerIfc {

    void publish(CategoryEvent productEvent);
}
