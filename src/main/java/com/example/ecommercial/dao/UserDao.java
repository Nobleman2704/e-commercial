package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntitiesByChatId(Long chatId);



    @Query(value = "update users set user_state = :state where chat_id = :id",
            nativeQuery = true)
    void updateUserStateByChatId(
            @Param("id") Long chatId,
            @Param("state") String userState);
}
