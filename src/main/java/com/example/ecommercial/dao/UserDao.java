package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntitiesByChatId(Long chatId);

    @Query(value = "update users set userState = :state where chatId = :chatId",
            nativeQuery = true)
    void updateUserStateByChatId(
           @Param("chatId") Long chatId,
           @Param("state") UserState userState);
}
