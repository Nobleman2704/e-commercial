package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryDao extends JpaRepository<ProductCategoryEntity, Long> {
    Optional<ProductCategoryEntity> findByName(String name);
}
