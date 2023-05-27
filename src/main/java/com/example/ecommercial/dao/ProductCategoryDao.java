package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryDao extends JpaRepository<ProductCategoryEntity, Long> {

}
