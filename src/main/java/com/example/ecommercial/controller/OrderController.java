package com.example.ecommercial.controller;

import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.OrderGetResponse;
import com.example.ecommercial.domain.dto.response.UserOrdersGetResponse;
import com.example.ecommercial.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@EnableMethodSecurity
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get_all")
    public ModelAndView getAllOrders(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<UserOrdersGetResponse>> response = orderService.getALl();
        modelAndView.addObject("userOrders", response.getData());
        modelAndView.addObject("status", 3);
        return modelAndView;
    }

    @PostMapping("/change_status")
    public ModelAndView changeStatus(
            @ModelAttribute("status") String status,
            @ModelAttribute("orderId") Long orderId
    ){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<UserOrdersGetResponse>> response =  orderService
                .changeStatus(orderId, status);
        modelAndView.addObject("userOrders", response.getData());
        modelAndView.addObject("message", response.getMessage());
        modelAndView.addObject("status", 3);
        return modelAndView;
    }
}