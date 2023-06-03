package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketDao extends JpaRepository<BasketEntity, Long> {
    void deleteBasketEntitiesByProductsId(Long productId);
    Optional<List<BasketEntity>> findBasketEntitiesByUsersChatId(Long chatId);
    Optional<BasketEntity> findBasketEntitiesByUsersChatIdAndProductsId(Long chatId, Long productId);
}
