package com.youssef.gamal.ecommerce.microservice.category.commands.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @Column(unique = true, nullable = false)
	private String name;

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
