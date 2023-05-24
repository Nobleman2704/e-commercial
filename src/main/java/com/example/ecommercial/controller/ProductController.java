package com.example.ecommercial.controller;

import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.dto.BaseResponse;
import com.example.ecommercial.dto.ProductPostRequest;
import com.example.ecommercial.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/add_product")
    public String addProduct(@ModelAttribute ProductPostRequest productPostRequest, Model model){
        BaseResponse<ProductEntity> response = productService.save(productPostRequest);
        model.addAttribute("response", response);
        return "html file name";
    }

}
