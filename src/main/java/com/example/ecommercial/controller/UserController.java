package com.example.ecommercial.controller;

import com.example.ecommercial.controller.dto.request.UserCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.UserGetResponse;
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

    @PostMapping("/add")
    public ModelAndView addUser(
            @Valid @ModelAttribute ("user") UserCreateAndUpdateRequest userCreateAndUpdateRequest,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("status", 4);
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        }else {
            BaseResponse response = userService.save(userCreateAndUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        modelAndView.addObject("users", userService
                .getALl(0).getData());
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateUser(
            @Valid @ModelAttribute ("user") UserCreateAndUpdateRequest userUpdateRequest,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        String viewName;
        if (bindingResult.hasErrors()){
            UserGetResponse data = userService.getById(userUpdateRequest.getId()).getData();
            modelAndView.addObject("user", data);
            modelAndView.addObject("message", extractAllErrors(bindingResult));
            viewName = "userUpdatePage";
        }else {
            BaseResponse response = userService.update(userUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
            modelAndView.addObject("status", 4);
            modelAndView.addObject("users", userService
                    .getALl(0).getData());
            viewName = "dashboard";
        }
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    @GetMapping("/update-page/{id}")
    public ModelAndView inUpdatePage(@PathVariable("id") Long id){
            ModelAndView modelAndView = new ModelAndView("userUpdatePage");
        UserGetResponse data = userService.getById(id).getData();
        modelAndView.addObject("user", data);
            return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(
            @PathVariable("id") Long userId
    ){
        userService.delete(userId);

        ModelAndView modelAndView = new ModelAndView("dashboard", "message", "deleted");
        modelAndView.addObject("status", 4);
        modelAndView.addObject("users", userService
                .getALl(0).getData());
        return modelAndView;
    }

    @GetMapping("/get_all")
    public ModelAndView getAllUsers(){
        BaseResponse<List<UserGetResponse>> response = userService
                .getALl(0);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("users", response.getData());
        modelAndView.addObject("status", 4);
        return modelAndView;
    }

    public static List<String> extractAllErrors(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage() + "\n\n")
                .toList();
    }
}
