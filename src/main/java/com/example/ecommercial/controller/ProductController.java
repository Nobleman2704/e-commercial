package com.example.ecommercial.controller;

import com.example.ecommercial.domain.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.example.ecommercial.controller.UserController.extractAllErrors;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ModelAndView addProduct(
            @Valid @ModelAttribute ProductCreateAndUpdateRequest productCreateRequest,
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
            @Valid @ModelAttribute("product")ProductCreateAndUpdateRequest productUpdateRequest,
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
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("products", productService.getALl().getData());
        modelAndView.addObject("status", 2);
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
