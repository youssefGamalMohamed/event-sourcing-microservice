package eg.intercom.ppo.revamp.productcommandservice.events;

import eg.intercom.ppo.revamp.productcommandservice.dtos.ProductDto;
import eg.intercom.ppo.revamp.productcommandservice.enums.ProductEventType;
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
    private ProductDto product;


}
