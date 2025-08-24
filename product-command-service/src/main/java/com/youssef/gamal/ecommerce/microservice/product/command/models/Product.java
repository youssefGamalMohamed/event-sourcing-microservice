package com.youssef.gamal.ecommerce.microservice.product.command.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private double price;

    private int quantity;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    @LastModifiedBy
    private String lastModifiedBy;
}
