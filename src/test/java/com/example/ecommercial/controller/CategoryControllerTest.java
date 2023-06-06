package com.example.ecommercial.controller;

import com.example.ecommercial.ECommercialApplication;
import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.example.ecommercial.service.category.CategoryService;
import lombok.SneakyThrows;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ECommercialApplication.class)
@AutoConfigureMockMvc

class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductCategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    private CategoryCreateAndUpdateRequest categoryDto;

    @BeforeEach
    void setUp() {
        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .name("VIVO")
                .build();

    }

    @SneakyThrows
    @Test
    void addCategory() {
        ModelAndView modelAndView = mockMvc.perform(
                post("/category/add")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("nobleman")
                                .password("1234"))
                        .flashAttr("category", categoryDto))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("success", message);
    }
    @SneakyThrows
    @Test
    void addCategoryFail() {
        categoryService.save(categoryDto);

        ModelAndView modelAndView = mockMvc.perform(
                        post("/category/add")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("category", categoryDto))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("category name already exists: " + categoryDto.getName(), message);
    }

    @SneakyThrows
    @Test
    void updateCategory() {

        categoryService.save(categoryDto);
        categoryDto.setId(1L);
        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .id(1L)
                .name("REDMI")
                .build();


        ModelAndView modelAndView = mockMvc.perform(
                        post("/category/update")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("category",categoryDto ))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("Updated", message);


    }

    @SneakyThrows
    @Test
    void getAllCategories() {

        categoryService.save(categoryDto);
        categoryDto.setName("PHONE");
        categoryService.save(categoryDto);


        ModelAndView modelAndView = mockMvc.perform(
                        MockMvcRequestBuilders.get("/category/get_all")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("pageNumber", 0))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        List<ProductGetResponse> response = (List<ProductGetResponse>) model.get("categories");
        assertEquals(response.size(),2);

    }
}