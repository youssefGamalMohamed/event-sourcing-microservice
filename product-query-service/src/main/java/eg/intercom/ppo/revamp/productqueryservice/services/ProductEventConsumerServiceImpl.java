package eg.intercom.ppo.revamp.productqueryservice.services;

import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductEventConsumerServiceImpl {


    @KafkaListener(topics = "products-event", groupId = "product-group")
    public void consumeProductEvent(ProductEvent productEvent) {
        log.info("Received product event: {}", productEvent);
    }


}
