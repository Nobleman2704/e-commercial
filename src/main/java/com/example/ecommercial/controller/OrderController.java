package com.example.ecommercial.controller;

import com.example.ecommercial.bot.ECommercialBot;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.UserOrdersGetResponse;
import com.example.ecommercial.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@EnableMethodSecurity
public class OrderController {
    private final OrderService orderService;
    private final ECommercialBot eCommercialBot;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or  hasAnyAuthority('GET_ORDER')")
    @GetMapping("/get_all")
    public ModelAndView getAllOrders(
            @RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber
    ) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<UserOrdersGetResponse>> response = orderService.getALl(pageNumber);
        modelAndView.addObject("pages", response.getTotalPageAmount());
        modelAndView.addObject("userOrders", response.getData());
        modelAndView.addObject("status", 3);
        return modelAndView;
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or  hasAnyAuthority('CHANGE_ORDER_STATUS')")
    @PostMapping("/change_status")
    public ModelAndView changeStatus(
            @ModelAttribute("status") String status,
            @ModelAttribute("orderId") Long orderId
    ) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<UserOrdersGetResponse>> response = orderService
                .changeStatus(orderId, status);
        String message = response.getMessageToUser();
        long chatId = response.getChatId();
        eCommercialBot.sendMessageToUser(message, chatId);
        modelAndView.addObject("userOrders", response.getData());
        modelAndView.addObject("message", response.getMessage());
        modelAndView.addObject("status", 3);
        modelAndView.addObject("pages", response.getTotalPageAmount());
        return modelAndView;
    }
}