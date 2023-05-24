package com.example.ecommercial.controller;

import com.example.ecommercial.dto.request.UserCreatePostRequest;
import com.example.ecommercial.dto.responce.BaseResponse;
import com.example.ecommercial.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add-user")
    public BaseResponse addUser(
            @ModelAttribute("user")
            UserCreatePostRequest userCreatePostRequest) {
        return userService.create(userCreatePostRequest);
    }
}
