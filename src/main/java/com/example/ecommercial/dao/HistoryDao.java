package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryDao extends JpaRepository<HistoryEntity, Long> {
    Optional<List<HistoryEntity>> findHistoryEntitiesByUsersChatId(Long chatId);
}
