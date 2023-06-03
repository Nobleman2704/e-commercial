package com.example.ecommercial.service.category;

import com.example.ecommercial.controller.converter.CategoryConverter;
import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceTest {

    private ProductCategoryEntity categoryEntity;
    private CategoryConverter categoryConverter;
    private CategoryCreateAndUpdateRequest categoryDto;

    @Autowired
    private ProductCategoryDao categoryDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;



    @BeforeEach
    void setUp() {
        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .name("PHONE")
                .parentId(null)
                .build();
    }

    @Test
    void successSave() {
        BaseResponse<List<ProductCategoryGetResponse>> save = categoryService.save(categoryDto);

        assertEquals(save.getStatus(), 200);

        Optional<ProductCategoryEntity> byName = categoryDao.findByName(categoryDto.getName());
        assertTrue(byName.isPresent());
    }

    @Test
    void failSave() {
        BaseResponse<List<ProductCategoryGetResponse>> save = categoryService.save(categoryDto);

        assertEquals(save.getStatus(), 401);

        Optional<ProductCategoryEntity> byName = categoryDao.findByName(categoryDto.getName());
        assertTrue(byName.isEmpty());
    }

    @Test
    void successUpdate() {
        categoryDao.save(modelMapper.map(categoryDto, ProductCategoryEntity.class));

        categoryDto = CategoryCreateAndUpdateRequest.builder()
                .id(1L)
                .name("PHONE112")
                .parentId(null)
                .build();

        BaseResponse update = categoryService.update(categoryDto);

        assertEquals(update.getStatus(), 200);
    }

    @Test
    void getALl() {
    }

    @Test
    void testGetALl() {
    }

    @Test
    void getById() {
    }
}