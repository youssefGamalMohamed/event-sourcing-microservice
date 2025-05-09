package eg.intercom.ppo.revamp.productcommandservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {


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
