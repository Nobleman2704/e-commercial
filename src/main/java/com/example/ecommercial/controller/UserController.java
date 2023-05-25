package com.example.ecommercial.controller;

import com.example.ecommercial.domain.dto.request.UserCreateRequest;
import com.example.ecommercial.domain.dto.request.UserUpdateRequest;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.UserGetResponse;
import com.example.ecommercial.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {
    private final UserService userService;

    @GetMapping("/add_user_test")
    public ModelAndView getUserAddPage(){
        return new ModelAndView("user-add-test");
    }


    @PostMapping("/add")
    public ModelAndView addUser(
            @Valid @ModelAttribute ("user") UserCreateRequest userCreateRequest,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("user-add-test");
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        }else {
            BaseResponse response = userService.save(userCreateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public ModelAndView updateUser(
            @Valid @ModelAttribute ("user") UserUpdateRequest userUpdateRequest,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        }else {
            BaseResponse response = userService.update(userUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id") Long userId
    ){
        userService.delete(userId);
        return new ModelAndView("viewName", "message", "deleted");
    }

    @GetMapping("/get_all")
    public ModelAndView getAllUsers(){
        BaseResponse<List<UserGetResponse>> response = userService.getALl();
        ModelAndView modelAndView = new ModelAndView("viewName");
        modelAndView.addObject("users", response.getData());
        return modelAndView;
    }

    public static List<String> extractAllErrors(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage() + "\n")
                .toList();
    }
}
