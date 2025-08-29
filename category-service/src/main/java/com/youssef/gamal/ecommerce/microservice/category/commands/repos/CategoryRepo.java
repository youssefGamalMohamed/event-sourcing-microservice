package com.youssef.gamal.ecommerce.microservice.category.commands.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {

}
