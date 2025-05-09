package eg.intercom.ppo.revamp.productqueryservice.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {

    @Id
    private String id;

    @Field
    private String originalId;

    private String name;

    private String description;

    private double price;

    private int quantity;

    @Field
    @CreatedDate
    private LocalDateTime creationDate;

    @Field
    @CreatedBy
    private String createdBy;

    @Field
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Field
    @LastModifiedBy
    private String lastModifiedBy;

    @Field
    private String eventType;

}
