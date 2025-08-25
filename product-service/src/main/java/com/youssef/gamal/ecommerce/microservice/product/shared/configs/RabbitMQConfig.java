package com.youssef.gamal.ecommerce.microservice.product.shared.configs;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange-names.ecommerce_exchange}")
    private String ecommerce_exchange;

    @Value("${rabbitmq.routing-keys.product_service_routing_key}")
    private String product_service_routing_key;

    @Value("${rabbitmq.queues-names.product_queue}")
    private String product_queue;
    
    @Bean
    public DirectExchange ecommerceExchange() {
        return new DirectExchange(this.ecommerce_exchange);
    }

 
    @Bean
    public Queue productQueue() {
        return new Queue(this.product_queue);
    }
    
    @Bean
    public Binding binding_productQueue_to_ecommerceExchange() {
        return BindingBuilder.bind(productQueue())
                .to(ecommerceExchange())
                .with(this.product_service_routing_key);
    }
    
    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(RabbitTemplate rabbitTemplate) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jackson2MessageConverter());
        factory.setConnectionFactory(rabbitTemplate.getConnectionFactory());
        return factory;
    }
}
