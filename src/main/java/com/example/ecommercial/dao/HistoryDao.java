package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryDao extends JpaRepository<HistoryEntity, Long> {
}
