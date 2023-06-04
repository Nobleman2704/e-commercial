package com.example.ecommercial.service.user;

import com.example.ecommercial.controller.dto.request.UserCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.UserGetResponse;
import com.example.ecommercial.domain.enums.UserRole;
import com.example.ecommercial.domain.enums.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    UserCreateAndUpdateRequest user;


    @BeforeEach
    void setUp() {
        user = UserCreateAndUpdateRequest.builder()
                .userRoles(List.of(UserRole.USER))
                .name("Asadbek")
                .username("nobleman")
                .password("1234")
                .build();
    }

    @Test
    void saveWithSuccess() {
        BaseResponse response = userService.save(user);

        assertEquals(200, response.getStatus());
    }

    @Test
    void saveWithExists() {
        userService.save(user);
        BaseResponse response = userService.save(user);

        assertEquals(401, response.getStatus());
    }

    @Test
    void updateWithSuccess() {
        userService.save(user);

        user = UserCreateAndUpdateRequest.builder()
                .id(1L)
                .username("asadbek")
                .password("1234")
                .build();

        BaseResponse response = userService.update(user);

        assertEquals(200, response.getStatus());
    }
    @Test
    void updateWithExists() {
        userService.save(user);

        user.setUsername("monster");

        userService.save(user);

        user = UserCreateAndUpdateRequest.builder()
                .id(2L)
                .username("nobleman")
                .password("1234")
                .build();

        BaseResponse response = userService.update(user);

        assertEquals(401, response.getStatus());
    }


    @Test
    void delete() {
        userService.save(user);

        userService.delete(1L);

        BaseResponse<UserGetResponse> response = userService.getById(1L);
        assertNull(response.getData());
    }

    @Test
    void getById() {
        userService.save(user);

        BaseResponse<UserGetResponse> response = userService.getById(1L);
        assertNotNull(response.getData());
    }

    @Test
    void getALl() {
        userService.save(user);

        user.setUsername("freeman");
        userService.save(user);

        BaseResponse<List<UserGetResponse>> response = userService
                .getALl(0);

        assertEquals(2, response.getData().size());
    }

    @Test
    void getAllBotUsers() {
        User user1 = new User();
        user1.setUserName("freeman");
        user1.setFirstName("Asadbek");

        long chatId = 4564564L;

        userService.saveBotUser(chatId, user1);
        user1.setUserName("nobleman");
        userService.saveBotUser(
                chatId+7,
                user1);

        BaseResponse<List<UserGetResponse>> response = userService
                .getAllBotUsers(0);

        assertEquals(2, response.getData().size());
    }

    @Test
    void getUserState() {
        User user1 = new User();
        user1.setUserName("freeman");
        user1.setFirstName("Asadbek");

        long chatId = 4564564L;

        userService.saveBotUser(chatId, user1);

        BaseResponse<UserState> response = userService
                .getUserState(chatId);

        assertEquals(UserState.REGISTERED, response.getData());
    }

    @Test
    void saveBotUser() {
        User user1 = new User();
        user1.setUserName("freeman");
        user1.setFirstName("Asadbek");

        long chatId = 4564564L;

        userService.saveBotUser(chatId, user1);

        BaseResponse<UserGetResponse> response = userService.getById(1L);

        assertEquals("Asadbek", response.getData().getName());
    }

    @Test
    void updateState() {
        User user1 = new User();
        user1.setUserName("freeman");
        user1.setFirstName("Asadbek");

        long chatId = 4564564L;

        userService.saveBotUser(chatId, user1);

        userService.updateState(chatId, UserState.IDLE);

        BaseResponse<UserState> response = userService
                .getUserState(chatId);

        assertEquals(UserState.IDLE, response.getData());
    }

    @Test
    void getUserBalance() {
        User user1 = new User();
        user1.setUserName("freeman");
        user1.setFirstName("Asadbek");

        long chatId = 4564564L;

        userService.saveBotUser(chatId, user1);

        userService.addBalance("45", chatId);

        BaseResponse<Double> response = userService.getUserBalance(chatId);

        assertEquals(45L, response.getData());
    }

    @Test
    void addBalance() {

    }
}