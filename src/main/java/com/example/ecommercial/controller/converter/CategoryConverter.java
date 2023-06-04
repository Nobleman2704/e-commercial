package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.ProductCategoryGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryConverter {

    private final ModelMapper modelMapper;

    public ProductCategoryEntity toCategoryEntity(CategoryCreateAndUpdateRequest createAndUpdateRequest){
        return modelMapper.map(createAndUpdateRequest, ProductCategoryEntity.class);
    }

    public List<ProductCategoryGetResponse> toCategoryGetDto(List<ProductCategoryEntity> content) {
        return modelMapper
                .map(content, new TypeToken<List<ProductCategoryGetResponse>>(){}
                                .getType());
    }
    public ProductCategoryGetResponse toCategoryGetDto(ProductCategoryEntity category) {
        return modelMapper.map(category, ProductCategoryGetResponse.class);
    }
}
