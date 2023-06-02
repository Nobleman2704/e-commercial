package com.example.ecommercial.service.category;

import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    public BaseResponse<List<ProductCategoryGetResponse>> save(ProductCategoryEntity category) {
        ProductCategoryEntity productCategory = modelMapper
                .map(createAndUpdateRequest, ProductCategoryEntity.class);

        Long categoryId = createAndUpdateRequest.getParentId();
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
                message = "product name already exists:" + productCategory.getName();
                status = 401;
            }
        }else {
            try {
                productCategoryDao.save(productCategory);
                message = "success";
                status = 200;
            } catch (Exception e) {
                message = "product name already exists:" + productCategory.getName();
                status = 401;
            }
        }
        BaseResponse<List<ProductCategoryGetResponse>> response = getALl(0);
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    public BaseResponse update(ProductCategoryEntity category) {
        Long id = category.getId();
        ProductCategoryEntity productCategory = productCategoryDao.findById(id).get();
        modelMapper.map(category, productCategory);

        try {
            productCategoryDao.save(productCategory);
        } catch (Exception e) {
            return BaseResponse.builder()
                    .message(productCategory.getName()+ " name already exists")
                    .status(401)
                    .build();
        }
        BaseResponse<List<ProductCategoryGetResponse>> response = getALl(0);
        response.setMessage("updated");
        return response;
    }



    public BaseResponse<List<ProductCategoryGetResponse>> getALl(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        Page<ProductCategoryEntity> categoryEntityPage = productCategoryDao
                .findAll(pageable);
        int totalPages = categoryEntityPage.getTotalPages();

        return BaseResponse.<List<ProductCategoryGetResponse>>builder()
                .totalPageAmount((totalPages==0)?0:totalPages-1)
                .status(200)
                .data(modelMapper
                        .map(categoryEntityPage.getContent(),
                                new TypeToken<List<ProductCategoryGetResponse>>(){}
                                        .getType()))
                .build();
    }

    public BaseResponse<List<ProductCategoryGetResponse>> getALl() {
        return BaseResponse.<List<ProductCategoryGetResponse>>builder()
                .status(200)
                .data(modelMapper
                        .map(productCategoryDao.findAll(),
                                new TypeToken<List<ProductCategoryGetResponse>>(){}
                                        .getType()))
                .build();
    }
}
