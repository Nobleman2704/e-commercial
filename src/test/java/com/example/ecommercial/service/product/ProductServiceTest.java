package com.example.ecommercial.service.product;

import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.controller.dto.response.UserGetResponse;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryDao categoryDao;

    ProductCreateAndUpdateRequest product;

    ProductCategoryEntity categoryEntity;

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
    void saveWithSuccess() {
        categoryDao.save(categoryEntity);

        BaseResponse response = productService.save(product);

        assertEquals(200, response.getStatus());
    }

    @Test
    void saveWithExists() {
        categoryDao.save(categoryEntity);

        productService.save(product);
        BaseResponse response = productService.save(product);

        assertEquals(404, response.getStatus());
    }

    @Test
    void updateWithSuccess() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        product = ProductCreateAndUpdateRequest.builder()
                .id(1L)
                .price(650)
                .amount(7)
                .build();

        BaseResponse response = productService.update(product);
        assertEquals(200, response.getStatus());
    }

    @Test
    void updateWithExists() {
        categoryDao.save(categoryEntity);

        productService.save(product);

        product.setName("SamsungS8");

        productService.save(product);

        product = ProductCreateAndUpdateRequest.builder()
                .id(2L)
                .name("Iphone12")
                .price(650)
                .amount(7)
                .build();

        BaseResponse response = productService.update(product);
        assertEquals(401, response.getStatus());
    }

    @Test
    void delete() {
        categoryDao.save(categoryEntity);

        productService.save(product);

        BaseResponse response = productService.delete(1L);
        assertEquals(200, response.getStatus());
    }

    @Test
    void getById() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        BaseResponse<ProductGetResponse> response = productService.getById(1L);
        assertNotNull(response.getData());
    }

    @Test
    void getALl() {
        categoryDao.save(categoryEntity);
        productService.save(product);

        product.setName("Samsung");
        productService.save(product);

        BaseResponse<List<ProductGetResponse>> response = productService.getALl(0);
        assertEquals(response.getData().size(), 2);
    }

    @Test
    void getProductsByCategoryId() {
        categoryDao.save(categoryEntity);

        productService.save(product);

        BaseResponse<List<ProductGetResponse>> productsByCategoryId = productService.getProductsByCategoryId(1L);
        assertEquals(productsByCategoryId.getStatus(), 200);
    }

    @Test
    void changeProductAmount() {
        categoryDao.save(categoryEntity);

        productService.save(product);

        BaseResponse<List<ProductGetResponse>> response = productService.changeProductAmount(1L, 3);
        assertEquals(response.getMessage(), "Amount added");
    }
}