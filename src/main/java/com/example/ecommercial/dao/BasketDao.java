package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.BaseEntity;
import com.example.ecommercial.domain.entity.BasketEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface BasketDao extends JpaRepository<BasketEntity, Long> {
    @Modifying
    int deleteBasketEntitiesByProducts(ProductEntity product);
    Optional<BasketEntity> findBasketEntitiesByUsersAndProducts(UserEntity user, ProductEntity product);
}
