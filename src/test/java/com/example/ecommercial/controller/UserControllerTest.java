package com.example.ecommercial.controller;

import com.example.ecommercial.ECommercialApplication;
import com.example.ecommercial.controller.dto.request.UserCreateAndUpdateRequest;
import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import com.example.ecommercial.service.user.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ECommercialApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDao userDao;

    private UserCreateAndUpdateRequest user;

    @BeforeEach
    void setUp() {
        user = UserCreateAndUpdateRequest.builder()
                .name("Asadbek")
                .username("freeman")
                .password("Asadbek1234")
                .userRoles(List.of(UserRole.USER))
                .build();

        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Test
    void addUserWithBindingResultError() {
        user.setPassword("1234");
        ModelAndView modelAndView = mockMvc.perform(
                        post("/user/add")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("user", user))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertNotEquals("saved", message);
    }

    @SneakyThrows
    @Test
    void addUserWithSuccess(){
        ModelAndView modelAndView = mockMvc.perform(
                        post("/user/add")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("user", user))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("saved", message);
    }

    @Test
    @SneakyThrows
    void addUserWithExistingUsername(){
        userDao.save(modelMapper.map(user, UserEntity.class));
        ModelAndView modelAndView = mockMvc.perform(
                        post("/user/add")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("user", user))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals(user.getUsername() + " already exists", message);
    }



    @Test
    void updateUser() {
    }

    @Test
    void inUpdatePage() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getALlBotUsers() {
    }

    @Test
    void extractAllErrors() {
    }
}