package com.youssef.gamal.ecommerce.microservice.product.shared.events;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent implements Serializable {

    private String id;
    private String eventType;

    private String name;
    private String description; // nullable
    private double price;
    private int quantity;

    private Instant creationDate;      // nullable, maps Avro timestamp-millis
    private String createdBy;          // nullable
    private Instant lastModifiedDate;  // nullable
    private String lastModifiedBy;     // nullable

    private long timestamp;
}
