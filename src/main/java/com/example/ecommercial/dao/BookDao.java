package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<BookEntity, Long> {
}
