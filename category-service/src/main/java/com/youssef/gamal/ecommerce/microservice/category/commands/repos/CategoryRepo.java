package com.youssef.gamal.ecommerce.microservice.category.commands.repos;


import com.youssef.gamal.ecommerce.microservice.category.commands.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {

    Optional<Category> findByName(String name);
}
