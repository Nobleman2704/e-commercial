package com.example.ecommercial.service.category;

import com.example.ecommercial.controller.converter.CategoryConverter;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProductCategoryDao productCategoryDao;
    private final ModelMapper modelMapper;
    private final CategoryConverter categoryConverter;

    public BaseResponse<List<ProductCategoryGetResponse>> save(CategoryCreateAndUpdateRequest categoryDto) {
        ProductCategoryEntity productCategory = categoryConverter.toCategoryEntity(categoryDto);

        Long categoryId = categoryDto.getParentId();
        String message;
        int status;

        if (categoryId != null){
            ProductCategoryEntity parentCategory = productCategoryDao.findById(categoryId).get();
            try {
                parentCategory.getProductCategories().add(productCategory);
                productCategory.setCategories(parentCategory);
                productCategoryDao.save(parentCategory);
                message = "success";
                status = 200;
            } catch (Exception e) {
                message = "category name already exists: " + productCategory.getName();
                status = 401;
            }
        }else {
            try {
                productCategoryDao.save(productCategory);
                message = "success";
                status = 200;
            } catch (Exception e) {
                message = "category name already exists: " + productCategory.getName();
                status = 401;
            }
        }
        return BaseResponse.of(message, status);
    }

    public BaseResponse update(CategoryCreateAndUpdateRequest category) {
        Long id = category.getId();
        ProductCategoryEntity productCategory = productCategoryDao.findById(id).get();
        modelMapper.map(category, productCategory);

        try {
            productCategoryDao.save(productCategory);
        } catch (Exception e) {
            return BaseResponse.of(
                    productCategory.getName()+ " name already exists",
                    401);
        }
        return BaseResponse.of("Updated", 200);
    }

    public BaseResponse<List<ProductCategoryGetResponse>> getALl(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<ProductCategoryEntity> categoryEntityPage = productCategoryDao
                .findAll(pageable);
        int totalPages = categoryEntityPage.getTotalPages();

        return BaseResponse.of(
                "success",
                200,
                categoryConverter.toCategoryGetDto(categoryEntityPage.getContent()),
                (totalPages==0)?0:totalPages-1);
    }

    public BaseResponse<List<ProductCategoryGetResponse>> getALl() {
        return BaseResponse.of(
                "success",
                200,
                categoryConverter.toCategoryGetDto(productCategoryDao.findAll()));
    }

    public BaseResponse<ProductCategoryGetResponse>  getById(Long id) {
        ProductCategoryEntity category = productCategoryDao.findById(id).get();

        return BaseResponse.of(
                "success",
                200,
                categoryConverter.toCategoryGetDto(category));
    }
}
