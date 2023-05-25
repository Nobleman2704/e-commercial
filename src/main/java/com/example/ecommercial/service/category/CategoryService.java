package com.example.ecommercial.service.category;

import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.domain.dto.request.CategoryCreateRequest;
import com.example.ecommercial.domain.dto.request.CategoryUpdateRequest;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.domain.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<
        CategoryCreateRequest,
        BaseResponse,
        CategoryUpdateRequest> {
    private final ProductCategoryDao productCategoryDao;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse save(CategoryCreateRequest categoryCreateRequest) {
        ProductCategoryEntity productCategory = modelMapper
                .map(categoryCreateRequest, ProductCategoryEntity.class);

        Long categoryId = categoryCreateRequest.getParentId();

        if (categoryId != null){
            productCategory.setCategories(productCategoryDao.findById(categoryId).get());
        }
        try {
            productCategoryDao.save(productCategory);
        } catch (Exception e) {
            return BaseResponse.builder()
                    .status(401)
                    .message("product name already exists: " + productCategory.getName())
                    .build();
        }
        return BaseResponse.builder()
                .status(200)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse update(CategoryUpdateRequest categoryUpdateRequest) {
        Long id = categoryUpdateRequest.getId();
        ProductCategoryEntity productCategory = productCategoryDao
                .findById(id).get();
        modelMapper.map(categoryUpdateRequest, productCategory);
        try {
            productCategoryDao.save(productCategory);
        } catch (Exception e) {
            return BaseResponse.builder()
                    .message(productCategory.getName()+ " name already exists")
                    .status(401)
                    .build();
        }
        return BaseResponse.builder()
                .message("success")
                .status(200)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {
        return null;
    }

    @Override
    public BaseResponse getById(Long id) {
        return null;
    }

    @Override
    public BaseResponse<List<ProductCategoryGetResponse>> getALl() {
        return BaseResponse.<List<ProductCategoryGetResponse>>builder()
                .data(modelMapper
                        .map(productCategoryDao.findAll(),
                                new TypeToken<List<ProductCategoryGetResponse>>(){}.getType()))
                .build();
    }
}
