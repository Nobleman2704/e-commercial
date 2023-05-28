package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.OrderEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends JpaRepository<OrderEntity, Long> {
    Optional<List<OrderEntity>> findOrderEntitiesByUsersId(Long id);
    boolean existsOrderEntitiesByProducts(ProductEntity product);
}
