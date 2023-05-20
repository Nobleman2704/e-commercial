package com.example.ecommercial.controller;

import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/home")
    public String test(){
        return "home";
    }

    @GetMapping("/add")
    public String addUser(){
        UserEntity userEntity = UserEntity.builder()
                .name("Asadbek")
                .username("nobleman")
                .password(passwordEncoder.encode("1234"))
                .userRoles(List.of(UserRole.SUPER_ADMIN))
                .build();

        userDao.save(userEntity);
        return "user added";
    }

}
