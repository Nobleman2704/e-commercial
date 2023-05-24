package com.example.ecommercial.controller;

import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.request.ProductCreateRequest;
import com.example.ecommercial.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add_product")
    public ModelAndView addProduct(
            @Valid @ModelAttribute ProductCreateRequest productCreateRequest,
            BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            List<String> errorList = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage() + "\n")
                    .toList();
            modelAndView.addObject("message", errorList);

        }else {
            BaseResponse response = productService.save(productCreateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        return modelAndView;
    }

}
