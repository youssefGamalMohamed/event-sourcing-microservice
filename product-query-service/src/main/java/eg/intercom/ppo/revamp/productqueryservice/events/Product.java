package eg.intercom.ppo.revamp.productqueryservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private LocalDateTime creationDate;

    private String createdBy;

    private LocalDateTime lastModifiedDate;

    private String lastModifiedBy;
}
