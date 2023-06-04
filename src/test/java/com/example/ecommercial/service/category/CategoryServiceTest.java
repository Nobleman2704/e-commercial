package com.example.ecommercial.service.category;

import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Autowired
    ProductCategoryDao categoryDao;
    @Autowired
    CategoryService categoryService;

    CategoryCreateAndUpdateRequest categoryDto;
    ProductCategoryEntity categoryEntity;




    @BeforeEach
    void setUp() {
        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .id(2L)
                .name("IPHONE")
                .parentId(1L)
                .build();
        categoryEntity = ProductCategoryEntity.builder()
                .name("PHONE")
                .build();
    }

    @Test
    void save() {
        categoryDao.save(categoryEntity);
        BaseResponse<List<ProductCategoryGetResponse>> response = categoryService.save(categoryDto);
        assertEquals(200,response.getStatus());
    }

    @Test
    void update() {
        categoryDao.save(categoryEntity);

        categoryService.save(categoryDto);

        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .id(2L)
                .name("SAMSUNG")
                .parentId(1L)
                .build();

        BaseResponse response = categoryService.update(categoryDto);

        assertEquals(200,response.getStatus());
    }

    @Test
    void getALl() {
        categoryDao.save(categoryEntity);
        categoryService.save(categoryDto);

        BaseResponse<List<ProductCategoryGetResponse>> response = categoryService.getALl();

        assertEquals(response.getStatus(),200);

    }

    @Test
    void testGetALl() {


    }

    @Test
    void getById() {
        categoryDao.save(categoryEntity);
        categoryService.save(categoryDto);

        BaseResponse response = categoryService.getById(1L);

        assertNotNull(response.getData());



    }
}