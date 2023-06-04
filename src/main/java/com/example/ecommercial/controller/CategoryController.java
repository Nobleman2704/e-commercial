package com.example.ecommercial.controller;

import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.example.ecommercial.controller.UserController.extractAllErrors;

@Controller
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ModelAndView addCategory(
            @Valid @ModelAttribute("category") CategoryCreateAndUpdateRequest createAndUpdateRequest,
            BindingResult bindingResult
            ){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("status", 1);
        if (bindingResult.hasErrors())
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        else{
            BaseResponse<List<ProductCategoryGetResponse>> response = categoryService.save(createAndUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
        }

        modelAndView.addObject("categories", categoryService.getALl(0).getData());
        modelAndView.addObject("pages", categoryService.getALl(0).getTotalPageAmount());
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateCategory(
            @Valid @ModelAttribute("category") CategoryCreateAndUpdateRequest categoryUpdateRequest,
            BindingResult bindingResult
            ){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("status", 1);
        if (bindingResult.hasErrors())
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        else {
            BaseResponse response = categoryService.update(categoryUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        modelAndView.addObject("pages", categoryService.getALl(0).getTotalPageAmount());
        modelAndView.addObject("categories", categoryService
                .getALl(0).getData());
        return modelAndView;
    }

    @GetMapping("get_all")
    public ModelAndView getAllCategories(
            @RequestParam(defaultValue = "0", name = "pageNumber") int pageNumber
    ){
        BaseResponse<List<ProductCategoryGetResponse>> response =
                categoryService.getALl(pageNumber);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("pages",response.getTotalPageAmount());
        modelAndView.addObject("categories", response.getData());
        modelAndView.addObject("status", 1);
        return modelAndView;
    }
}
