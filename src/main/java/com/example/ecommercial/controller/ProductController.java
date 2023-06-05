package com.example.ecommercial.controller;

import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.service.category.CategoryService;
import com.example.ecommercial.service.product.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ModelAndView addProduct(
            @Valid @ModelAttribute("product") ProductCreateAndUpdateRequest productCreateRequest,
            BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
        }else {
            BaseResponse response = productService.save(productCreateRequest);
            modelAndView.addObject("message", response.getMessage());
        }
        BaseResponse<List<ProductGetResponse>> response = productService.getALl(0);
        modelAndView.addObject("products", response.getData());
        modelAndView.addObject("pages", response.getTotalPageAmount());
        modelAndView.addObject("categories", categoryService.getALl().getData());
        modelAndView.addObject("status", 2);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateProduct(
            @Valid @ModelAttribute("product")ProductCreateAndUpdateRequest productUpdateRequest,
            BindingResult bindingResult
            ){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", extractAllErrors(bindingResult));
            modelAndView.addObject("product", productService.getById(productUpdateRequest.getId()).getData());
            modelAndView.setViewName("productUpdatePage");
        } else {
            BaseResponse response = productService.update(productUpdateRequest);
            modelAndView.addObject("message", response.getMessage());
            modelAndView.addObject("products", productService
                    .getALl(0).getData());
            modelAndView.addObject("pages", response.getTotalPageAmount());
            modelAndView.addObject("categories", categoryService
                    .getALl().getData());
            modelAndView.addObject("status", 2);
            modelAndView.setViewName("dashboard");
        }
        return modelAndView;
    }

    @GetMapping("/get_all")
    public ModelAndView getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber
    ){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<ProductGetResponse>> response = productService
                .getALl(pageNumber);
        modelAndView.addObject("products", response.getData());
        modelAndView.addObject("pages", response.getTotalPageAmount());
        modelAndView.addObject("categories", categoryService.getALl().getData());
        modelAndView.addObject("status", 2);
        return modelAndView;
    }

//    @GetMapping("/products-page")
//    public ModelAndView productsPage(){
//        ModelAndView modelAndView = new ModelAndView("dashboard");
//        modelAndView.addObject("products", productService.getALl().getData());
//        modelAndView.addObject("categories", categoryService.getALl().getData());
//        modelAndView.addObject("status", 2);
//        return modelAndView;
//    }

    @GetMapping("/update-page/{id}")
    public ModelAndView updatePage(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView("productUpdatePage");
        modelAndView.addObject("product", productService
                .getById(id).getData());
        return modelAndView;
    }

    @PostMapping("/add_amount")
    public ModelAndView changeAmount(
            @ModelAttribute("id") Long productId,
            @ModelAttribute("amount") int amount){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        BaseResponse<List<ProductGetResponse>> response = productService
                .changeProductAmount(productId, amount);
        modelAndView.addObject("message", response.getMessage());
        modelAndView.addObject("products", response.getData());
        modelAndView.addObject("categories", categoryService.getALl().getData());
        modelAndView.addObject("pages", response.getTotalPageAmount());

        modelAndView.addObject("status", 2);
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(
            @PathVariable("id") Long id
    ){
        BaseResponse response = productService.delete(id);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("products", productService
                .getALl(0).getData());
        modelAndView.addObject("categories", categoryService
                .getALl().getData());
        modelAndView.addObject("pages", response.getTotalPageAmount());
        modelAndView.addObject("message", response.getMessage());
        modelAndView.addObject("status", 2);
        return modelAndView;
    }
}
