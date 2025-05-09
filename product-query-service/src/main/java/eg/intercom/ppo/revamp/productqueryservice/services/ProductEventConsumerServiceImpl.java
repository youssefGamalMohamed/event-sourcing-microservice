package eg.intercom.ppo.revamp.productqueryservice.services;

import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;
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

    @KafkaListener(topics = "products-event", groupId = "product-group")
    public void consumeProductEvent(ProductEvent productEvent) {
        log.info("Received product event: {}", productEvent);
        // Save Received Product Event to DB
        productViewService.addProductView(productViewMapper.toProductView(productEvent));
    }


}
