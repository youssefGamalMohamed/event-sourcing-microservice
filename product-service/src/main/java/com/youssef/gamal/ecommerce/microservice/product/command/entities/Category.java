package com.youssef.gamal.ecommerce.microservice.product.command.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Column(name = "original_id", nullable = false)
    private String originalId;

    @Column(name = "snapshot_id", nullable = false)
    private String snapshotId;
}