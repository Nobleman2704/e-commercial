package com.example.ecommercial.dao;

import com.example.ecommercial.ECommercialApplication;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.listener.AuditingAwareImpl;
import com.example.ecommercial.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        user = UserEntity.builder()
                .name("Asadbek")
                .username("nobleman")
                .password("1234")
                .chatId(123L)
                .build();
    }

    @Test
    void findUserEntityByUsername() {
        userDao.save(user);

        Optional<UserEntity> optionalUserEntity = userDao
                .findUserEntityByUsername("nobleman");

//        System.out.println("optionalUserEntity.get() = " + optionalUserEntity.get());


        assertTrue(optionalUserEntity.isPresent());
    }

    @Test
    void findUserEntitiesByChatId() {
        userDao.save(user);
        Optional<UserEntity> optionalUser = userDao.findUserEntitiesByChatId(user.getChatId());

        assertTrue(optionalUser.isPresent());
    }

    @Test
    void findUserEntitiesByOrderEntitiesIsNotEmptyAndChatIdNotNull() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> userEntityPage = userDao
                .findUserEntitiesByOrderEntitiesIsNotEmptyAndChatIdNotNull(pageable);

        assertNotNull(userEntityPage);
    }

    @Test
    void findUserEntitiesByChatIdIsNull() {
        userDao.save(user);
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> optionalBotUsers = userDao
                .findUserEntitiesByChatIdIsNull(pageable);

        List<UserEntity> userEntityList = optionalBotUsers.getContent();

        assertTrue(userEntityList.isEmpty());
    }

    @Test
    void findUserEntitiesByChatIdIsNotNull() {
        userDao.save(user);
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserEntity> optionalBotUsers = userDao
                .findUserEntitiesByChatIdIsNotNull(pageable);

        List<UserEntity> userEntityList = optionalBotUsers.getContent();

        assertFalse(userEntityList.isEmpty());
    }
}