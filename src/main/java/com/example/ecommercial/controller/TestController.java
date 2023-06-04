package com.example.ecommercial.controller;

import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {


    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @GetMapping("/add")
    public String addUser(){
        ArrayList<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.SUPER_ADMIN);
        UserEntity userEntity = UserEntity.builder()
                .name("Asadbek")
                .username("nobleman")
                .password(passwordEncoder.encode("1234"))
                .userRoles(userRoles)
                .build();

        userDao.save(userEntity);
        return "user added";
    }

    @PostMapping("/dashboard-page")
    public String jumpToDashBoard(){
        return "dashboard";
    }
}
