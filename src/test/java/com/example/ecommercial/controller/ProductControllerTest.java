package com.example.ecommercial.controller;

import com.example.ecommercial.ECommercialApplication;
import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.jboss.logging.NDC.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


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
    private ProductService productService;

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
                .build();}

    @SneakyThrows
    @Test
    void addProductSuccess() {
        categoryDao.save(categoryEntity);

        ModelAndView modelAndView = mockMvc.perform(
                post("/product/add")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user("nobleman")
                                .password("1234"))
                        .flashAttr("product", product))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("saved", message);
    }

    @SneakyThrows
    @Test
    void addProductFail() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        ModelAndView modelAndView = mockMvc.perform(
                        post("/product/add")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("product", product))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("This name already exists", message);
    }

    @SneakyThrows
    @Test
    void updateProduct() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        product.setId(1L);
        product.setName("Samsung");
        product.setAmount(12);

        ModelAndView modelAndView = mockMvc.perform(
                        post("/product/update")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("product", product))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("Updated", message);
    }

    @SneakyThrows
    @Test
    void getAllProducts() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        product.setName("Samsung");
        productService.save(product);

        ModelAndView modelAndView = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/get_all")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("pageNumber", 0))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        List<ProductGetResponse> responses = (List<ProductGetResponse>) model.get("products");

        assertEquals(2, responses.size());
    }

    @Test
    void updatePage() {
    }

    @SneakyThrows
    @Test
    void changeAmount() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        ModelAndView modelAndView = mockMvc.perform(
                        post("/product/add_amount")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234"))
                                .flashAttr("id", 1L)
                                .flashAttr("amount", 6))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("Amount added", message);
    }

    @SneakyThrows
    @Test
    void deleteProduct() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        ModelAndView modelAndView = mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/delete/1")
                                .with(SecurityMockMvcRequestPostProcessors
                                        .user("nobleman")
                                        .password("1234")))
                .andReturn().getModelAndView();

        Map<String, Object> model = modelAndView.getModel();

        String message = (String) model.get("message");

        assertEquals("product has been deleted", message);
    }
}