package com.youssef.gamal.ecommerce.microservice.product.command.services;

import com.youssef.gamal.ecommerce.microservice.product.shared.events.*;

public interface ProductEventProducerIfc {

    void publish(ProductEvent productEvent);
}
