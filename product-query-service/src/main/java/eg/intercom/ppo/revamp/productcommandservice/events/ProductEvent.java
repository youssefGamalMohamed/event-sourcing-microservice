package eg.intercom.ppo.revamp.productcommandservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent implements Serializable {

    private ProductEventType eventType;
    private Product product;


}
