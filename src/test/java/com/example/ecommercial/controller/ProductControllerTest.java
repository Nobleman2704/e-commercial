package com.example.ecommercial.controller;

import com.example.ecommercial.ECommercialApplication;
import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.RequestEntity.post;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ECommercialApplication.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao categoryDao;

    private ProductCreateAndUpdateRequest product;

    private ProductCategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        product = ProductCreateAndUpdateRequest.builder()
                .name("Iphone12")
                .description("good phone")
                .price(600)
                .amount(4)
                .categoryId(1L)
                .build();
        categoryEntity = ProductCategoryEntity.builder()
                .name("PHONE")
                .build();
    }

    @Test
    void addProduct() {
//        ModelAndView modelAndView = mockMvc.perform(
//                post("/product/add")
//                        .with(SecurityMockMvcRequestPostProcessors
//                                .)
//        )
    }

    @Test
    void updateProduct() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void updatePage() {
    }

    @Test
    void changeAmount() {
    }

    @Test
    void deleteProduct() {
    }
}