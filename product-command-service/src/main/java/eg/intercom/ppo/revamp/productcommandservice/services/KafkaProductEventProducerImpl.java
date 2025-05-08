package eg.intercom.ppo.revamp.productcommandservice.services;

import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
        kafkaTemplate.send(topicName, productEvent);
        log.debug("Product event published successfully");
    }
}
