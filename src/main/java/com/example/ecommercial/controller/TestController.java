package com.example.ecommercial.controller;

import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.UserRole;
import com.example.ecommercial.domain.listener.AuditingAwareImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final AuditingAwareImpl auditingAware;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/home")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("object", auditingAware.getCurrentAuditor().get());
        return modelAndView;
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
