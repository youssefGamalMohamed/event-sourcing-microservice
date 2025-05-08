package eg.intercom.ppo.revamp.productcommandservice.services;

import eg.intercom.ppo.revamp.productcommandservice.events.ProductEvent;

public interface ProductEventProducerIfc {

    void publish(ProductEvent productEvent);
}
