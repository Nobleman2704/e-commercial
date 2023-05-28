package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.BaseEntity;
import com.example.ecommercial.domain.entity.BasketEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketDao extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findBasketEntitiesByUsersAndProducts(UserEntity user, ProductEntity product);
}
