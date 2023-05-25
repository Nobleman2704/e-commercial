package com.example.ecommercial.controller;

import com.example.ecommercial.domain.dto.request.ProductUpdateRequest;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.ecommercial.controller.UserController.extractAllErrors;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ModelAndView addProduct(
            @Valid @ModelAttribute ProductCreateRequest productCreateRequest,
            BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        }else {
            BaseResponse response = productService.save(productCreateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        return modelAndView;
    }

    @PutMapping("/update")
    public ModelAndView updateProduct(
            @Valid @ModelAttribute("product")ProductUpdateRequest productUpdateRequest,
            BindingResult bindingResult
            ){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors())
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        else {
            BaseResponse response = productService.update(productUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/get_all")
    public ModelAndView getAllProducts(){
        ModelAndView modelAndView = new ModelAndView("viewName");
        modelAndView.addObject("products", productService.getALl().getData());
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteProduct(
            @PathVariable("id") Long productId
    ){
        BaseResponse response = productService.delete(productId);
        return new ModelAndView("viewName", "message", response.getMessage());
    }
}
