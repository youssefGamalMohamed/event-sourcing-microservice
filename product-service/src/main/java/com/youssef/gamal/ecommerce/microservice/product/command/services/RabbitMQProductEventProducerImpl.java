package com.youssef.gamal.ecommerce.microservice.product.command.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.youssef.gamal.ecommerce.microservice.product.shared.events.ProductEvent;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RabbitMQProductEventProducerImpl implements ProductEventProducerIfc {

    @Value("${rabbitmq.exchange-names.ecommerce_exchange}")
    private String ecommerce_exchange;

    @Value("${rabbitmq.routing-keys.product_service_routing_key}")
    private String product_service_routing_key;

    
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQProductEventProducerImpl(RabbitTemplate rabbitTemplate) {
    	this.rabbitTemplate = rabbitTemplate;
    }
    
	@Override
	public void publish(ProductEvent productEvent) {
		log.info("publish({})", productEvent);
		rabbitTemplate.convertAndSend(ecommerce_exchange, product_service_routing_key, productEvent);
		log.info("publish(): --> ✅ message Sent Successfully");
	}

}
