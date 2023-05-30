package com.example.ecommercial.dao;

import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntitiesByChatId(Long chatId);
    Page<UserEntity> findUserEntitiesByOrderEntitiesIsNotEmptyAndChatIdNotNull(Pageable pageable);
    Page<UserEntity> findUserEntitiesByChatIdIsNull(Pageable pageable);

//    List<UserEntity> findUserEntitiesByUserRolesNotIn(UserRole[][] userRoles);
//    Optional<List<UserEntity>> findUserEntitiesByUserRolesContains(UserRole userRole);
}
